import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createEmployeeApi } from "../../api/employeeApi";

function AddEmployeePage() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    employeeCode: "",
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    department: "",
    designation: "",
    dateOfJoining: "",
    basicSalary: "",
    status: "ACTIVE",
  });

  const [error, setError] = useState("");

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
      await createEmployeeApi({
        ...formData,
        basicSalary: Number(formData.basicSalary),
      });

      navigate("/employees");
    } catch (err) {
      setError("Failed to create employee");
      console.error(err);
    }
  };

  return (
    <div style={{ maxWidth: "700px", background: "#fff", padding: "24px" }}>
      <h2>Add Employee</h2>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <form onSubmit={handleSubmit}>
        <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "16px" }}>
          <input name="employeeCode" placeholder="Employee Code" value={formData.employeeCode} onChange={handleChange} />
          <input name="firstName" placeholder="First Name" value={formData.firstName} onChange={handleChange} />
          <input name="lastName" placeholder="Last Name" value={formData.lastName} onChange={handleChange} />
          <input name="email" placeholder="Email" value={formData.email} onChange={handleChange} />
          <input name="phone" placeholder="Phone" value={formData.phone} onChange={handleChange} />
          <input name="department" placeholder="Department" value={formData.department} onChange={handleChange} />
          <input name="designation" placeholder="Designation" value={formData.designation} onChange={handleChange} />
          <input name="dateOfJoining" type="date" value={formData.dateOfJoining} onChange={handleChange} />
          <input name="basicSalary" type="number" placeholder="Basic Salary" value={formData.basicSalary} onChange={handleChange} />

          <select name="status" value={formData.status} onChange={handleChange}>
            <option value="ACTIVE">ACTIVE</option>
            <option value="INACTIVE">INACTIVE</option>
          </select>
        </div>

        <div style={{ marginTop: "20px" }}>
          <button type="submit">Save Employee</button>
        </div>
      </form>
    </div>
  );
}

export default AddEmployeePage;