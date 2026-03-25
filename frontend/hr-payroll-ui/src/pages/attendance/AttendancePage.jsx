import { useEffect, useState } from "react";
import { createAttendanceApi, getAllAttendanceApi } from "../../api/attendanceApi";

function AttendancePage() {
  const [attendanceList, setAttendanceList] = useState([]);
  const [formData, setFormData] = useState({
    employeeId: "",
    attendanceDate: "",
    checkInTime: "",
    checkOutTime: "",
    status: "PRESENT",
    remarks: "",
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadAttendance = async () => {
    try {
      setLoading(true);
      const data = await getAllAttendanceApi();
      setAttendanceList(data);
      setError("");
    } catch (err) {
      setError("Failed to load attendance records");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadAttendance();
  }, []);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      await createAttendanceApi({
        ...formData,
        employeeId: Number(formData.employeeId),
      });

      setFormData({
        employeeId: "",
        attendanceDate: "",
        checkInTime: "",
        checkOutTime: "",
        status: "PRESENT",
        remarks: "",
      });

      loadAttendance();
      setError("");
    } catch (err) {
      setError("Failed to mark attendance");
      console.error(err);
    }
  };

  return (
    <div>
      <h2>Attendance Management</h2>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <div style={{ background: "#fff", padding: "24px", marginBottom: "24px" }}>
        <h3>Mark Attendance</h3>

        <form onSubmit={handleSubmit}>
          <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "16px" }}>
            <input
              type="number"
              name="employeeId"
              placeholder="Employee ID"
              value={formData.employeeId}
              onChange={handleChange}
              required
            />

            <input
              type="date"
              name="attendanceDate"
              value={formData.attendanceDate}
              onChange={handleChange}
              required
            />

            <input
              type="datetime-local"
              name="checkInTime"
              value={formData.checkInTime}
              onChange={handleChange}
            />

            <input
              type="datetime-local"
              name="checkOutTime"
              value={formData.checkOutTime}
              onChange={handleChange}
            />

            <select
              name="status"
              value={formData.status}
              onChange={handleChange}
            >
              <option value="PRESENT">PRESENT</option>
              <option value="ABSENT">ABSENT</option>
              <option value="HALF_DAY">HALF_DAY</option>
              <option value="WFH">WFH</option>
            </select>

            <input
              type="text"
              name="remarks"
              placeholder="Remarks"
              value={formData.remarks}
              onChange={handleChange}
            />
          </div>

          <div style={{ marginTop: "16px" }}>
            <button type="submit">Save Attendance</button>
          </div>
        </form>
      </div>

      <div style={{ background: "#fff", padding: "24px" }}>
        <h3>Attendance Records</h3>

        {loading ? (
          <p>Loading attendance records...</p>
        ) : (
          <table border="1" cellPadding="10" style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead>
              <tr>
                <th>ID</th>
                <th>Employee ID</th>
                <th>Date</th>
                <th>Check In</th>
                <th>Check Out</th>
                <th>Status</th>
                <th>Remarks</th>
              </tr>
            </thead>
            <tbody>
              {attendanceList.map((item) => (
                <tr key={item.id}>
                  <td>{item.id}</td>
                  <td>{item.employeeId}</td>
                  <td>{item.attendanceDate}</td>
                  <td>{item.checkInTime || "-"}</td>
                  <td>{item.checkOutTime || "-"}</td>
                  <td>{item.status}</td>
                  <td>{item.remarks || "-"}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default AttendancePage;