import { useEffect, useState } from "react";
import { generatePayrollApi, getAllPayrollApi } from "../../api/payrollApi";
import { useAuth } from "../../context/AuthContext";

function PayrollPage() {
  const { auth } = useAuth();

  const [payrollList, setPayrollList] = useState([]);
  const [selectedPayslip, setSelectedPayslip] = useState(null);
  const [formData, setFormData] = useState({
    employeeId: "",
    payrollMonth: "",
    basicSalary: "",
    allowances: "",
    deductions: "",
    workingDays: "",
    presentDays: "",
    leaveDays: "",
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadPayrollRecords = async () => {
    try {
      setLoading(true);
      const data = await getAllPayrollApi();
      setPayrollList(data);
      setError("");
    } catch (err) {
      setError("Failed to load payroll records");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadPayrollRecords();
  }, []);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleGeneratePayroll = async (event) => {
    event.preventDefault();

    try {
      const payload = {
        employeeId: Number(formData.employeeId),
        payrollMonth: formData.payrollMonth,
        basicSalary: Number(formData.basicSalary),
        allowances: Number(formData.allowances || 0),
        deductions: Number(formData.deductions || 0),
        workingDays: Number(formData.workingDays),
        presentDays: Number(formData.presentDays),
        leaveDays: Number(formData.leaveDays),
      };

      const generatedRecord = await generatePayrollApi(payload);

      setSelectedPayslip(generatedRecord);
      setFormData({
        employeeId: "",
        payrollMonth: "",
        basicSalary: "",
        allowances: "",
        deductions: "",
        workingDays: "",
        presentDays: "",
        leaveDays: "",
      });

      await loadPayrollRecords();
      setError("");
    } catch (err) {
      setError("Failed to generate payroll");
      console.error(err);
    }
  };

  const canManagePayroll = auth.role === "HR" || auth.role === "ADMIN";

  return (
    <div>
      <h2>Payroll Management</h2>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {canManagePayroll && (
        <div style={{ background: "#fff", padding: "24px", marginBottom: "24px" }}>
          <h3>Generate Payroll</h3>

          <form onSubmit={handleGeneratePayroll}>
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
                type="month"
                name="payrollMonth"
                value={formData.payrollMonth}
                onChange={handleChange}
                required
              />

              <input
                type="number"
                name="basicSalary"
                placeholder="Basic Salary"
                value={formData.basicSalary}
                onChange={handleChange}
                required
              />

              <input
                type="number"
                name="allowances"
                placeholder="Allowances"
                value={formData.allowances}
                onChange={handleChange}
              />

              <input
                type="number"
                name="deductions"
                placeholder="Manual Deductions"
                value={formData.deductions}
                onChange={handleChange}
              />

              <input
                type="number"
                name="workingDays"
                placeholder="Working Days"
                value={formData.workingDays}
                onChange={handleChange}
                required
              />

              <input
                type="number"
                name="presentDays"
                placeholder="Present Days"
                value={formData.presentDays}
                onChange={handleChange}
                required
              />

              <input
                type="number"
                name="leaveDays"
                placeholder="Leave Days"
                value={formData.leaveDays}
                onChange={handleChange}
                required
              />
            </div>

            <div style={{ marginTop: "16px" }}>
              <button type="submit">Generate Payroll</button>
            </div>
          </form>
        </div>
      )}

      {selectedPayslip && (
        <div style={{ background: "#fff", padding: "24px", marginBottom: "24px", border: "1px solid #ddd" }}>
          <h3>Payslip Preview</h3>
          <p><strong>Employee ID:</strong> {selectedPayslip.employeeId}</p>
          <p><strong>Payroll Month:</strong> {selectedPayslip.payrollMonth}</p>
          <p><strong>Basic Salary:</strong> {selectedPayslip.basicSalary}</p>
          <p><strong>Allowances:</strong> {selectedPayslip.allowances}</p>
          <p><strong>Total Deductions:</strong> {selectedPayslip.deductions}</p>
          <p><strong>Net Salary:</strong> {selectedPayslip.netSalary}</p>
          <p><strong>Working Days:</strong> {selectedPayslip.workingDays}</p>
          <p><strong>Present Days:</strong> {selectedPayslip.presentDays}</p>
          <p><strong>Leave Days:</strong> {selectedPayslip.leaveDays}</p>
          <p><strong>Status:</strong> {selectedPayslip.status}</p>
          <p><strong>Generated At:</strong> {selectedPayslip.generatedAt}</p>
        </div>
      )}

      <div style={{ background: "#fff", padding: "24px" }}>
        <h3>Payroll Records</h3>

        {loading ? (
          <p>Loading payroll records...</p>
        ) : (
          <table border="1" cellPadding="10" style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead>
              <tr>
                <th>ID</th>
                <th>Employee ID</th>
                <th>Month</th>
                <th>Basic Salary</th>
                <th>Allowances</th>
                <th>Deductions</th>
                <th>Net Salary</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {payrollList.map((item) => (
                <tr key={item.id}>
                  <td>{item.id}</td>
                  <td>{item.employeeId}</td>
                  <td>{item.payrollMonth}</td>
                  <td>{item.basicSalary}</td>
                  <td>{item.allowances}</td>
                  <td>{item.deductions}</td>
                  <td>{item.netSalary}</td>
                  <td>{item.status}</td>
                  <td>
                    <button onClick={() => setSelectedPayslip(item)}>
                      View Payslip
                    </button>
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

export default PayrollPage;