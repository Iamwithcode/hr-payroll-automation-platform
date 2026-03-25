import axios from "axios";

const REPORT_BASE_URL = "http://localhost:8080/api/v1/reports";

const getAuthHeader = () => {
  const token = localStorage.getItem("token");

  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const getSummaryReportApi = async () => {
  const response = await axios.get(`${REPORT_BASE_URL}/summary`, getAuthHeader());
  return response.data;
};