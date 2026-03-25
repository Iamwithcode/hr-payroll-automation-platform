import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import SummaryCard from "../../components/common/SummaryCard";
import { useAuth } from "../../context/AuthContext";
import { getAllEmployeesApi } from "../../api/employeeApi";
import { getAllAttendanceApi } from "../../api/attendanceApi";
import { getAllLeavesApi } from "../../api/leaveApi";
import { getAllPayrollApi } from "../../api/payrollApi";

function DashboardPage() {
  const { auth } = useAuth();

  const [summary, setSummary] = useState({
    employees: 0,
    attendance: 0,
    leaves: 0,
    payroll: 0,
  });

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const isAdminOrHr = auth.role === "ADMIN" || auth.role === "HR";
  const isEmployee = auth.role === "EMPLOYEE";

  useEffect(() => {
    const loadDashboardData = async () => {
      if (!isAdminOrHr) {
        setLoading(false);
        return;
      }

      try {
        setLoading(true);

        const [employees, attendance, leaves, payroll] = await Promise.all([
          getAllEmployeesApi(),
          getAllAttendanceApi(),
          getAllLeavesApi(),
          getAllPayrollApi(),
        ]);

        setSummary({
          employees: employees.length,
          attendance: attendance.length,
          leaves: leaves.length,
          payroll: payroll.length,
        });

        setError("");
      } catch (err) {
        setError("Failed to load dashboard data");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    loadDashboardData();
  }, [isAdminOrHr]);

  return (
    <div>
      <h2>Dashboard</h2>
      <p>Welcome to HR Payroll Dashboard.</p>
      <p><strong>Logged in user:</strong> {auth.username}</p>
      <p><strong>Role:</strong> {auth.role}</p>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {loading ? (
        <p>Loading dashboard...</p>
      ) : (
        <>
          {isAdminOrHr && (
            <>
              <div
                style={{
                  display: "grid",
                  gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))",
                  gap: "16px",
                  margin: "24px 0"
                }}
              >
                <SummaryCard
                  title="Total Employees"
                  value={summary.employees}
                  subtitle="Registered employee records"
                />
                <SummaryCard
                  title="Attendance Records"
                  value={summary.attendance}
                  subtitle="Tracked attendance entries"
                />
                <SummaryCard
                  title="Leave Requests"
                  value={summary.leaves}
                  subtitle="All submitted leave requests"
                />
                <SummaryCard
                  title="Payroll Records"
                  value={summary.payroll}
                  subtitle="Generated payroll entries"
                />
              </div>

              <div style={{ background: "#fff", padding: "24px", marginTop: "16px" }}>
                <h3>Quick Actions</h3>
                <div style={{ display: "flex", gap: "16px", flexWrap: "wrap" }}>
                  <Link to="/employees"><button>Go to Employees</button></Link>
                  <Link to="/attendance"><button>Go to Attendance</button></Link>
                  <Link to="/leaves"><button>Go to Leaves</button></Link>
                  <Link to="/payroll"><button>Go to Payroll</button></Link>
                </div>
              </div>
            </>
          )}

          {isEmployee && (
            <>
              <div
                style={{
                  display: "grid",
                  gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))",
                  gap: "16px",
                  margin: "24px 0"
                }}
              >
                <SummaryCard
                  title="My Role"
                  value={auth.role}
                  subtitle="Current logged-in access level"
                />
                <SummaryCard
                  title="Portal Access"
                  value="Employee"
                  subtitle="Use available employee features"
                />
              </div>

              <div style={{ background: "#fff", padding: "24px", marginTop: "16px" }}>
                <h3>My Quick Actions</h3>
                <div style={{ display: "flex", gap: "16px", flexWrap: "wrap" }}>
                  <Link to="/leaves"><button>My Leaves</button></Link>
                  <Link to="/attendance"><button>Attendance</button></Link>
                </div>
              </div>
            </>
          )}
        </>
      )}
    </div>
  );
}

export default DashboardPage;