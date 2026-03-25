import { useEffect, useState } from "react";
import { applyLeaveApi, approveLeaveApi, getAllLeavesApi, rejectLeaveApi } from "../../api/leaveApi";
import { useAuth } from "../../context/AuthContext";

function LeavePage() {
  const { auth } = useAuth();

  const [leaveList, setLeaveList] = useState([]);
  const [formData, setFormData] = useState({
    employeeId: "",
    leaveType: "SICK",
    startDate: "",
    endDate: "",
    reason: "",
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadLeaves = async () => {
    try {
      setLoading(true);
      const data = await getAllLeavesApi();
      setLeaveList(data);
      setError("");
    } catch (err) {
      setError("Failed to load leave requests");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadLeaves();
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
      await applyLeaveApi({
        ...formData,
        employeeId: Number(formData.employeeId),
      });

      setFormData({
        employeeId: "",
        leaveType: "SICK",
        startDate: "",
        endDate: "",
        reason: "",
      });

      await loadLeaves();
      setError("");
    } catch (err) {
      setError("Failed to apply leave");
      console.error(err);
    }
  };

  const handleApprove = async (id) => {
    try {
      await approveLeaveApi(id, 2);
      await loadLeaves();
    } catch (err) {
      setError("Failed to approve leave");
      console.error(err);
    }
  };

  const handleReject = async (id) => {
    try {
      await rejectLeaveApi(id, 2);
      await loadLeaves();
    } catch (err) {
      setError("Failed to reject leave");
      console.error(err);
    }
  };

  const canManageLeaves = auth.role === "HR" || auth.role === "ADMIN";

  return (
    <div>
      <h2>Leave Management</h2>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <div style={{ background: "#fff", padding: "24px", marginBottom: "24px" }}>
        <h3>Apply Leave</h3>

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

            <select
              name="leaveType"
              value={formData.leaveType}
              onChange={handleChange}
            >
              <option value="SICK">SICK</option>
              <option value="CASUAL">CASUAL</option>
              <option value="EARNED">EARNED</option>
            </select>

            <input
              type="date"
              name="startDate"
              value={formData.startDate}
              onChange={handleChange}
              required
            />

            <input
              type="date"
              name="endDate"
              value={formData.endDate}
              onChange={handleChange}
              required
            />

            <input
              type="text"
              name="reason"
              placeholder="Reason"
              value={formData.reason}
              onChange={handleChange}
              required
              style={{ gridColumn: "1 / span 2" }}
            />
          </div>

          <div style={{ marginTop: "16px" }}>
            <button type="submit">Apply Leave</button>
          </div>
        </form>
      </div>

      <div style={{ background: "#fff", padding: "24px" }}>
        <h3>Leave Requests</h3>

        {loading ? (
          <p>Loading leave requests...</p>
        ) : (
          <table border="1" cellPadding="10" style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead>
              <tr>
                <th>ID</th>
                <th>Employee ID</th>
                <th>Type</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Total Days</th>
                <th>Reason</th>
                <th>Status</th>
                <th>Approved By</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {leaveList.map((item) => (
                <tr key={item.id}>
                  <td>{item.id}</td>
                  <td>{item.employeeId}</td>
                  <td>{item.leaveType}</td>
                  <td>{item.startDate}</td>
                  <td>{item.endDate}</td>
                  <td>{item.totalDays}</td>
                  <td>{item.reason}</td>
                  <td>{item.status}</td>
                  <td>{item.approvedBy ?? "-"}</td>
                  <td>
  {item.status === "PENDING" && canManageLeaves ? (
    <div style={{ display: "flex", gap: "8px" }}>
      <button onClick={() => handleApprove(item.id)}>Approve</button>
      <button onClick={() => handleReject(item.id)}>Reject</button>
    </div>
  ) : item.status === "APPROVED" ? (
    <span style={{ color: "green", fontWeight: "bold" }}>Approved</span>
  ) : item.status === "REJECTED" ? (
    <span style={{ color: "red", fontWeight: "bold" }}>Rejected</span>
  ) : (
    <td>
  {item.status === "APPROVED" ? (
    <span style={{ color: "green", fontWeight: "bold" }}>APPROVED</span>
  ) : item.status === "REJECTED" ? (
    <span style={{ color: "red", fontWeight: "bold" }}>REJECTED</span>
  ) : (
    <span style={{ color: "orange", fontWeight: "bold" }}>PENDING</span>
  )}
</td>
  )}
</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default LeavePage;