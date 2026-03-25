import axios from "axios";

const EMPLOYEE_BASE_URL = "http://localhost:8080/api/v1/employees";

const getAuthHeader = () => {
  const token = localStorage.getItem("token");

  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const getAllEmployeesApi = async () => {
  const response = await axios.get(EMPLOYEE_BASE_URL, getAuthHeader());
  return response.data;
};

export const getEmployeeByIdApi = async (id) => {
  const response = await axios.get(`${EMPLOYEE_BASE_URL}/${id}`, getAuthHeader());
  return response.data;
};

export const createEmployeeApi = async (employeeData) => {
  const response = await axios.post(EMPLOYEE_BASE_URL, employeeData, getAuthHeader());
  return response.data;
};

export const updateEmployeeApi = async (id, employeeData) => {
  const response = await axios.put(`${EMPLOYEE_BASE_URL}/${id}`, employeeData, getAuthHeader());
  return response.data;
};