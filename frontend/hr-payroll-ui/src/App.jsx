import { useEffect } from "react";
import axios from "axios";

function App() {
  useEffect(() => {
    axios.get("http://localhost:8080/health")
      .then(res => console.log(res.data))
      .catch(err => console.error(err));
  }, []);

  return <h1>HR Payroll Platform</h1>;
}

export default App;

// cd "E:\2026pro\1-5 new\hr-payroll-automation-platform\frontend\hr-payroll-ui"