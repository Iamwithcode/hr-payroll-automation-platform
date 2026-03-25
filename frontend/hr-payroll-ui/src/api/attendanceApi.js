import axios from "axios";

const ATTENDANCE_BASE_URL = "http://localhost:8080/api/v1/attendance";

const getAuthHeader = () => {
  const token = localStorage.getItem("token");

  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const getAllAttendanceApi = async () => {
  const response = await axios.get(ATTENDANCE_BASE_URL, getAuthHeader());
  return response.data;
};

export const createAttendanceApi = async (attendanceData) => {
  const response = await axios.post(ATTENDANCE_BASE_URL, attendanceData, getAuthHeader());
  return response.data;
};