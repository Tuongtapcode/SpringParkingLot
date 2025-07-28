import axios from "axios";

const BASE_URL = "http://localhost:8080/ParkingLot/api";
const endpoints = {
  parkingLots: "/parkinglots",
  parkingSpaces: "/parkingspaces",
};

export default axios.create({
  baseURL: BASE_URL,
});
