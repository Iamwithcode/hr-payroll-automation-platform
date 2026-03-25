import axios from "axios";

const PAYROLL_BASE_URL = "http://localhost:8080/api/v1/payroll";

const getAuthHeader = () => {
  const token = localStorage.getItem("token");

  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const generatePayrollApi = async (payrollData) => {
  const response = await axios.post(
    `${PAYROLL_BASE_URL}/generate`,
    payrollData,
    getAuthHeader()
  );
  return response.data;
};

export const getAllPayrollApi = async () => {
  const response = await axios.get(PAYROLL_BASE_URL, getAuthHeader());
  return response.data;
};

export const getPayrollByEmployeeIdApi = async (employeeId) => {
  const response = await axios.get(
    `${PAYROLL_BASE_URL}/employee/${employeeId}`,
    getAuthHeader()
  );
  return response.data;
};