import axios from "axios";

const AUTH_BASE_URL = "http://localhost:8080/api/v1/auth";

export const loginApi = async (loginData) => {
  const response = await axios.post(`${AUTH_BASE_URL}/login`, loginData);
  return response.data;
};