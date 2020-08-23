import React from "react";
import logo from "./logo.svg";
import "./App.css";
import Login from "./Components/Login";
import Navbar from "./Components/NavBar";
import RoomLayout from "./Components/RoomLayout";
function App() {
  return (
    <div className="App">
      <Navbar />
      <RoomLayout />
    </div>
  );
}

export default App;
