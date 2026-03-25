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
          gap: "20px",
          alignItems: "center",
          justifyContent: "space-between",
          background: "#fff"
        }}
      >
        <div style={{ display: "flex", gap: "20px", alignItems: "center" }}>
          <h3 style={{ margin: 0 }}>HR Payroll Platform</h3>
          <Link to="/dashboard">Dashboard</Link>
          <Link to="/employees">Employees</Link>
        </div>

        <div style={{ display: "flex", gap: "16px", alignItems: "center" }}>
          {auth.isAuthenticated ? (
            <>
              <span>{auth.username} ({auth.role})</span>
              <button onClick={handleLogout}>Logout</button>
            </>
          ) : (
            <Link to="/login">Login</Link>
          )}
        </div>
      </nav>

      <main style={{ padding: "24px" }}>
        <Outlet />
      </main>
    </div>
  );
}

export default MainLayout;