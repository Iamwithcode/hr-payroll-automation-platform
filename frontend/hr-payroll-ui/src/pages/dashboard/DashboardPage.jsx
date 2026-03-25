import { useAuth } from "../../context/AuthContext";

function DashboardPage() {
  const { auth } = useAuth();

  return (
    <div>
      <h2>Dashboard</h2>
      <p>Welcome to HR Payroll Dashboard.</p>
      <p><strong>Logged in user:</strong> {auth.username}</p>
      <p><strong>Role:</strong> {auth.role}</p>
    </div>
  );
}

export default DashboardPage;