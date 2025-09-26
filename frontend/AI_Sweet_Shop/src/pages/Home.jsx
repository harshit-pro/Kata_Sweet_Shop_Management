import React, { useEffect, useState } from "react";
import api from "../api/axios";
import SweetCard from "../components/SweetCard";

export default function Home() {
  const [sweets, setSweets] = useState([]);

  const fetchSweets = async () => {
    try {
      const res = await api.get("/sweets/all");
      setSweets(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load sweets");
    }
  };

  useEffect(() => { fetchSweets(); }, []);

  return (
    <div>
      <h2>Sweets</h2>
      {sweets.map(s => (
        <SweetCard key={s.id} sweet={s} onRefresh={fetchSweets} />
      ))}
    </div>
  );
}
