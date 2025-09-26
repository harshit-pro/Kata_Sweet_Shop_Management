import React, { useState, useEffect } from "react";
import api from "../api/axios";

export default function Admin() {
  const [name,setName]=useState("");
  const [category,setCategory]=useState("");
  const [price,setPrice]=useState(0);
  const [quantity,setQuantity]=useState(0);
  const [list,setList]=useState([]);

  const fetch = async () => {
    const res = await api.get("/sweets");
    setList(res.data);
  };

  useEffect(() => { fetch(); }, []);

  const create = async () => {
    await api.post("/sweets", { name, category, price, quantity });
    setName(""); setCategory(""); setPrice(0); setQuantity(0);
    fetch();
  };

  const del = async (id) => {
    await api.delete(`/sweets/${id}`);
    fetch();
  };

  return (
    <div>
      <h2>Admin</h2>
      <input placeholder="name" value={name} onChange={e=>setName(e.target.value)} />
      <input placeholder="category" value={category} onChange={e=>setCategory(e.target.value)} />
      <input type="number" placeholder="price" value={price} onChange={e=>setPrice(Number(e.target.value))} />
      <input type="number" placeholder="quantity" value={quantity} onChange={e=>setQuantity(Number(e.target.value))} />
      <button onClick={create}>Create</button>

      <hr />
      {list.map(s => (
        <div key={s.id}>
          {s.name} ({s.quantity}) <button onClick={()=>del(s.id)}>Delete</button>
        </div>
      ))}
    </div>
  );
}
