import { useEffect, useState } from "react";
import SummaryCard from "../../components/common/SummaryCard";
import { getSummaryReportApi } from "../../api/reportApi";
import { useAuth } from "../../context/AuthContext";

function ReportsPage() {
  const { auth } = useAuth();
  const [summary, setSummary] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const canViewReports = auth.role === "HR" || auth.role === "ADMIN";

  useEffect(() => {
    const loadReports = async () => {
      if (!canViewReports) {
        setLoading(false);
        return;
      }

      try {
        setLoading(true);
        const data = await getSummaryReportApi();
        setSummary(data);
        setError("");
      } catch (err) {
        setError("Failed to load reports");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    loadReports();
  }, [canViewReports]);

  if (!canViewReports) {
    return <p>You do not have access to reports.</p>;
  }

  return (
    <div>
      <h2>Reports</h2>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {loading ? (
        <p>Loading reports...</p>
      ) : summary && (
        <>
          <div
            style={{
              display: "grid",
              gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))",
              gap: "16px",
              margin: "24px 0"
            }}
          >
            <SummaryCard title="Total Employees" value={summary.totalEmployees} subtitle="All employee records" />
            <SummaryCard title="Active Employees" value={summary.activeEmployees} subtitle="Currently active employees" />
            <SummaryCard title="Attendance Records" value={summary.totalAttendanceRecords} subtitle="Tracked attendance entries" />
            <SummaryCard title="Payroll Records" value={summary.totalPayrollRecords} subtitle="Generated payroll entries" />
          </div>

          <div
            style={{
              display: "grid",
              gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))",
              gap: "16px",
              marginBottom: "24px"
            }}
          >
            <SummaryCard title="Total Leaves" value={summary.totalLeaveRequests} subtitle="Submitted leave requests" />
            <SummaryCard title="Pending Leaves" value={summary.pendingLeaves} subtitle="Waiting for action" />
            <SummaryCard title="Approved Leaves" value={summary.approvedLeaves} subtitle="Approved requests" />
            <SummaryCard title="Rejected Leaves" value={summary.rejectedLeaves} subtitle="Rejected requests" />
          </div>

          <div style={{ background: "#fff", padding: "24px", border: "1px solid #ddd", borderRadius: "8px" }}>
            <h3>Payroll Summary</h3>
            <p><strong>Total Net Salary Processed:</strong> {summary.totalNetSalary}</p>
            <p><strong>Inactive Employees:</strong> {summary.inactiveEmployees}</p>
          </div>
        </>
      )}
    </div>
  );
}

export default ReportsPage;