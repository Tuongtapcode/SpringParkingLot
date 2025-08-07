import axios from "axios";
import cookie from "react-cookies";
const BASE_URL = "http://localhost:8080/ParkingLot/api";
export const endpoints = {
  parkingLots: "/parkinglots",
  parkingSpaces: "/parkingspaces",
  register: "/register",
  login: "/login",
  profile: "/secure/profile",
  reservation: "/secure/reservation",
  upcoming: "/secure/upcoming/pending",
  pays: "/secure/pays",
  
};

export default axios.create({
  baseURL: BASE_URL,
});

export const authApis = () =>
  axios.create({
    baseURL: BASE_URL,
    headers: {
      Authorization: `Bearer ${cookie.load("token")}`,
    },
  });
