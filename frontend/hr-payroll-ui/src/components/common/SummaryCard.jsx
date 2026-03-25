function SummaryCard({ title, value, subtitle }) {
  return (
    <div
      style={{
        background: "#fff",
        padding: "20px",
        borderRadius: "8px",
        border: "1px solid #ddd",
        minHeight: "120px"
      }}
    >
      <h4 style={{ margin: "0 0 10px 0" }}>{title}</h4>
      <h2 style={{ margin: "0 0 10px 0" }}>{value}</h2>
      <p style={{ margin: 0, color: "#666" }}>{subtitle}</p>
    </div>
  );
}

export default SummaryCard;