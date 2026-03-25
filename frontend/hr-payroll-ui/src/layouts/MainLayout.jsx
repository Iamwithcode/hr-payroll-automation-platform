import { Link, Outlet } from "react-router-dom";

function MainLayout() {
  return (
    <div>
      <nav
        style={{
          padding: "16px 24px",
          borderBottom: "1px solid #ddd",
          display: "flex",
          gap: "20px",
          alignItems: "center"
        }}
      >
        <h3 style={{ margin: 0 }}>HR Payroll Platform</h3>
        <Link to="/dashboard">Dashboard</Link>
        <Link to="/employees">Employees</Link>
        <Link to="/login">Login</Link>
      </nav>

      <main style={{ padding: "24px" }}>
        <Outlet />
      </main>
    </div>
  );
}

export default MainLayout;