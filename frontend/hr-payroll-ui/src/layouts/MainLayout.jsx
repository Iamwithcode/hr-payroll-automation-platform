import { Link, Outlet, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function MainLayout() {
  const { auth, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div>
      <nav
        style={{
          padding: "16px 24px",
          borderBottom: "1px solid #ddd",
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          background: "#fff",
        }}
      >
        <div style={{ display: "flex", gap: "20px", alignItems: "center" }}>
          <h3 style={{ margin: 0 }}>HR Payroll Platform</h3>
          <Link to="/dashboard">Dashboard</Link>
          <Link to="/employees">Employees</Link>
          <Link to="/attendance">Attendance</Link>
          <Link to="/leaves">Leaves</Link>
        </div>

        <div style={{ display: "flex", gap: "12px", alignItems: "center" }}>
          <span>{auth.username} ({auth.role})</span>
          <button onClick={handleLogout}>Logout</button>
        </div>
      </nav>

      <main style={{ padding: "24px" }}>
        <Outlet />
      </main>
    </div>
  );
}

export default MainLayout;