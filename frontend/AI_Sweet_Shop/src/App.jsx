import React from "react";
import { Outlet, Link } from "react-router-dom";

export default function App() {
  const logout = () => {
    localStorage.removeItem("token");
    window.location.href = "/login";
  };

  return (
    <div>
      <header style={{ padding: 12, borderBottom: "1px solid #ddd" }}>
        <Link to="/">Home</Link> | <Link to="/admin">Admin</Link> |{" "}
        <Link to="/login">Login</Link> |{" "}
        <a onClick={logout} style={{ cursor: "pointer" }}>Logout</a>
      </header>
      <main style={{ padding: 12 }}>
        <Outlet />
      </main>
    </div>
  );
}
