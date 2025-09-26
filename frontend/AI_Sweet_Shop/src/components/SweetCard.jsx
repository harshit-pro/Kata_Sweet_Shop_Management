import React, { useState } from "react";
import api from "../api/axios";

export default function SweetCard({ sweet, onRefresh }) {
  const [qty,setQty] = useState(1);

  const purchase = async () => {
    try {
      await api.post(`/sweets/${sweet.id}/purchase`, { quantity: qty });
      alert("Purchased!");
      onRefresh();
    } catch (err) {
      alert("Purchase failed");
    }
  };

  return (
    <div style={{ border: "1px solid #ddd", padding: 8, marginBottom: 8 }}>
      <b>{sweet.name}</b> — {sweet.quantity} left, ₹{sweet.price}
      <br />
      <input type="number" min="1" max={sweet.quantity} value={qty} onChange={e=>setQty(Number(e.target.value))} />
      <button onClick={purchase} disabled={sweet.quantity <= 0}>Purchase</button>
    </div>
  );
}

