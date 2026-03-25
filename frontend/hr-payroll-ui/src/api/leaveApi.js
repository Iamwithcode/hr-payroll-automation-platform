import axios from "axios";

const LEAVE_BASE_URL = "http://localhost:8080/api/v1/leaves";

const getAuthHeader = () => {
  const token = localStorage.getItem("token");

  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const getAllLeavesApi = async () => {
  const response = await axios.get(LEAVE_BASE_URL, getAuthHeader());
  return response.data;
};

export const getLeavesByEmployeeIdApi = async (employeeId) => {
  const response = await axios.get(`${LEAVE_BASE_URL}/employee/${employeeId}`, getAuthHeader());
  return response.data;
};

export const applyLeaveApi = async (leaveData) => {
  const response = await axios.post(LEAVE_BASE_URL, leaveData, getAuthHeader());
  return response.data;
};

export const approveLeaveApi = async (id, approvedBy) => {
  const response = await axios.put(
    `${LEAVE_BASE_URL}/${id}/approve?approvedBy=${approvedBy}`,
    {},
    getAuthHeader()
  );
  return response.data;
};

export const rejectLeaveApi = async (id, approvedBy) => {
  const response = await axios.put(
    `${LEAVE_BASE_URL}/${id}/reject?approvedBy=${approvedBy}`,
    {},
    getAuthHeader()
  );
  return response.data;
};