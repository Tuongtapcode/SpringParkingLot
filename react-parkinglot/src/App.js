import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import ParkingLots from "./components/parkinglots/ParkingLots";
import ParkingBookingSystem from "./components/parkingspaces/ParkingSpaces";
import Register from "./components/users/Register";
import Login from "./components/users/Login";
import { useReducer } from "react";
import MyUserReducer from "./reducers/MyUserReducer";
import { MyUserContext } from "./configs/Contexts";
import UserProfile from "./components/users/Profile";
import Settings from "./components/users/Settings";
import ChangePassword from "./components/users/ChangePassword";
import Reservation from "./components/parkingspaces/Reservation";
import Payment from "./components/parkingspaces/Payments";

function App() {
  const [user, dispatch] = useReducer(MyUserReducer, null);
  return (
    <MyUserContext.Provider value={[user, dispatch]}>
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path="/" element={<ParkingLots />} />
          <Route path="/spaces" element={<ParkingBookingSystem />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />{" "}
          <Route path="/bookings" element={<Reservation />} />
          <Route path="/payments" element={<Payment />} />
          <Route path="/profile" element={<UserProfile />} />
          <Route path="/settings" element={<Settings />} />
          <Route path="/settings/changepassword" element={<ChangePassword />} />
        </Routes>

        <Footer />
      </BrowserRouter>
    </MyUserContext.Provider>
  );
}

export default App;
