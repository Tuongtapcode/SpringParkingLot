import {
  Bike,
  Car,
  CarIcon,
  Check,
  CircleParking,
  Clock,
  MapPin,
  Search,
} from "lucide-react";
import { useState, useEffect, useContext } from "react";
import {
  Container,
  Row,
  Col,
  Card,
  Button,
  Badge,
  Modal,
  Form,
  Alert,
  InputGroup,
} from "react-bootstrap";
import Apis, { authApis, endpoints } from "../../configs/Apis";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { MyUserContext } from "../../configs/Contexts";
import MySpinner from "../layout/MySpinner";

const ParkingBookingSystem = () => {
  const [parkinglots, setParkingLots] = useState([]);
  const [parkinglot, setParkingLot] = useState([]);
  const [user] = useContext(MyUserContext);
  const [params] = useSearchParams();
  const [keyword, setKeyword] = useState(params.get("kw") || "");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [parkingSpaces, setParkingSpaces] = useState([]);
  const [bookingForm, setBookingForm] = useState({
    startTime: "",
    endTime: "",
    hours: "",
    licensePlate: "",
  });
  const loadParkingLots = async () => {
    let response = await Apis.get(endpoints.parkingLots);
    setParkingLots(response.data);
  };

  const loadParkingLot = async () => {
    let url = endpoints.parkingLots;

    try {
      let pklId = params.get("pklId");
      let response = await Apis.get(url);
      if (pklId) {
        const lot = response.data.find((item) => item.id === parseInt(pklId));
        console.log(lot);
        setParkingLot(lot);
      } else {
      }
    } catch (err) {
      console.error("Error loading parking lots:", err);
    }
  };

  const loadParkingSpaces = async () => {
    let startTime = params.get("startTime");
    let endTime = params.get("endTime");
    if (startTime && endTime) {
      let url = endpoints.parkingSpaces;
      let paramsObj = {};

      let pklId = params.get("pklId");
      let vh = params.get("vh");
      let kw = params.get("kw");

      if (pklId) paramsObj.pklId = pklId;
      if (vh) paramsObj.vh = vh;
      if (kw) paramsObj.kw = kw;
      if (startTime) paramsObj.startTime = startTime;
      if (endTime) paramsObj.endTime = endTime;

      const queryString = new URLSearchParams(paramsObj).toString();
      if (queryString) url = `${url}?${queryString}`;

      try {
        setLoading(true);
        let response = await Apis.get(url);
        console.log(response.data);
        setParkingSpaces(response.data);
      } catch (err) {
        console.error("Error loading parking spaces:", err);
      } finally {
        setLoading(false);
      }
    }
  };
  useEffect(() => {
    loadParkingSpaces();
    loadParkingLot();
  }, [params]);
  useEffect(() => {
    const hours = getHourDifference(startTime, endTime);
    setBookingForm((prev) => ({
      ...prev,
      startTime,
      endTime,
      hours,
    }));
  }, [startTime, endTime]);
  useEffect(() => {
    loadParkingLots();
    const now = new Date();
    const oneHourLater = new Date(now.getTime() + 60 * 60 * 1000);

    const pad = (n) => n.toString().padStart(2, "0");

    const toInputFormat = (date) => {
      return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(
        date.getDate()
      )}T${pad(date.getHours())}:${pad(date.getMinutes())}`;
    };

    setStartTime(toInputFormat(now));
    setEndTime(toInputFormat(oneHourLater));
  }, []);

  function formatDateTime(input) {
    const date = new Date(input);
    const pad = (n) => n.toString().padStart(2, "0");
    return (
      `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(
        date.getDate()
      )}` + ` ${pad(date.getHours())}:${pad(date.getMinutes())}:00`
    );
  }

  const [showBookingModal, setShowBookingModal] = useState(false);
  const [selectedSpace, setSelectedSpace] = useState({
    name: "",
    vehicleType: "",
  });

  const [showSuccessAlert, setShowSuccessAlert] = useState(false);

  const getHourDifference = (start, end) => {
    if (!start || !end) return "";
    const startDate = new Date(start);
    const endDate = new Date(end);
    const diffMs = endDate - startDate;
    const diffHours = diffMs / (1000 * 60 * 60);
    return diffHours.toFixed(1); // làm tròn 2 chữ số
  };

  const getSlotColor = (status) => {
    switch (status) {
      case "AVAILABLE":
        return "#28a745";
      case "BOOKED":
        return "#ffc107";
      case "BLOCKED":
        return "#6c757d";
      case "OCCUPIED":
        return "#dc3545";
      default:
        return "#28a745";
    }
  };

  const getVehicleIcon = (vehicleType) => {
    return vehicleType === "CAR" ? <CarIcon /> : <Bike />;
  };

  const handleSlotClick = (slot) => {
    if (slot.status === "AVAILABLE") {
      setSelectedSpace(slot);
      setShowBookingModal(true);
    }
  };

  const Booking = async () => {
    try {
      const { startTime, endTime, vehiclePlate } = bookingForm;
      const start = new Date(startTime);
      const end = new Date(endTime);
      const isoStart = start.toISOString().slice(0, 19);
      const isoEnd = end.toISOString().slice(0, 19);
      const payload = [
        {
          parkingSpaceId: selectedSpace.id,
          startTime: isoStart,
          endTime: isoEnd,
          licensePlate: vehiclePlate,
        },
      ];
      let res = await authApis().post(
        endpoints.reservation,
        Object.values(payload)
      );
      if (res.status == 201) {
        console.log("Đặt chỗ thành công");
        setShowBookingModal(false);
        setShowSuccessAlert(true);
        setBookingForm({});
        // Ẩn thông báo sau 3 giây
        setTimeout(() => setShowSuccessAlert(false), 7000);
      }
    } catch (err) {
      console.error(err);
      console.log("Đặt chỗ thất bại!");
    }
  };

  // Thống kê
  const stats = {
    total: parkingSpaces.length,
    available: parkingSpaces.filter((s) => s.status === "AVAILABLE").length,
    booked: parkingSpaces.filter((s) => s.status === "BOOKED").length,
    blocked: parkingSpaces.filter((s) => s.status === "BLOCKED").length,
    occupited: parkingSpaces.filter((s) => s.status === "OCCUPIED").length,
  };
  if (loading) {
    return <MySpinner></MySpinner>;
  }
  return (
    <Container
      fluid
      className="py-4"
      style={{ backgroundColor: "#f8f9fa", minHeight: "100vh" }}
    >
      <Card className="mb-4 shadow-sm">
        <Card.Body>
          <Row>
            <Col md={6} lg={3} className="mb-3">
              <Form.Label>Tên chỗ đỗ</Form.Label>
              <InputGroup>
                <InputGroup.Text>
                  <MapPin size={16} />
                </InputGroup.Text>
                <Form.Control
                  value={keyword}
                  onChange={(e) => setKeyword(e.target.value)}
                  type="text"
                  placeholder="Tìm theo tên"
                  onKeyDown={(e) => {
                    if (e.key === "Enter") {
                      const newParams = new URLSearchParams(params);
                      newParams.set("kw", keyword);
                      navigate(`/spaces?${newParams.toString()}`);
                    }
                  }}
                />
              </InputGroup>
            </Col>

            <Col md={6} lg={2} className="mb-3">
              <Form.Label>Thời gian bắt đầu</Form.Label>
              <InputGroup>
                <InputGroup.Text>
                  <Clock size={16} />
                </InputGroup.Text>
                <Form.Control
                  type="datetime-local"
                  value={startTime}
                  onChange={(e) => setStartTime(e.target.value)}
                />
              </InputGroup>
            </Col>

            <Col md={6} lg={2} className="mb-3">
              <Form.Label>Thời gian kết thúc</Form.Label>
              <InputGroup>
                <InputGroup.Text>
                  <Clock size={16} />
                </InputGroup.Text>
                <Form.Control
                  type="datetime-local"
                  value={endTime}
                  onChange={(e) => setEndTime(e.target.value)}
                />
              </InputGroup>
            </Col>

            <Col md={6} lg={2} className="mb-3">
              <Form.Label>Loại xe</Form.Label>
              <InputGroup>
                <InputGroup.Text>
                  <Car size={16} />
                </InputGroup.Text>
                <Form.Select
                  value={params.get("vh") || ""}
                  onChange={(e) => {
                    const newParams = new URLSearchParams(params);
                    newParams.set("vh", e.target.value);
                    navigate(`/spaces?${newParams.toString()}`);
                  }}
                >
                  <option value="">Tất cả</option>
                  <option value="car">Ô tô</option>
                  <option value="moto">Xe máy</option>
                </Form.Select>
              </InputGroup>
            </Col>

            <Col md={6} lg={2} className="mb-3">
              <Form.Label>Bãi đỗ</Form.Label>
              <InputGroup>
                <InputGroup.Text>
                  <CircleParking size={16} />
                </InputGroup.Text>
                <Form.Select
                  value={params.get("pklId") || ""}
                  onChange={(e) => {
                    const newParams = new URLSearchParams(params);
                    newParams.set("pklId", e.target.value);
                    navigate(`/spaces?${newParams.toString()}`);
                  }}
                >
                  {parkinglots.map((p) => (
                    <option key={p.id} value={p.id}>
                      {p.name}
                    </option>
                  ))}
                </Form.Select>
              </InputGroup>
            </Col>

            <Col md={6} lg={1} className="mb-3 d-flex align-items-end">
              <Button
                variant="primary"
                className="w-100"
                onClick={() => {
                  const newParams = new URLSearchParams(params);
                  newParams.set("kw", keyword);
                  if (startTime)
                    newParams.set("startTime", formatDateTime(startTime));
                  if (endTime)
                    newParams.set("endTime", formatDateTime(endTime));
                  navigate(`/spaces?${newParams.toString()}`);
                }}
              >
                <Search size={16} />
              </Button>
            </Col>
          </Row>
        </Card.Body>
      </Card>

      {/* Header */}
      <Row className="mb-4">
        <Col>
          <Card className="shadow-sm">
            <Card.Body>
              <h2 className="text-center mb-3" style={{ color: "#2c3e50" }}>
                <CarIcon /> Hệ thống đặt chỗ đỗ xe
              </h2>
              <div className="text-center">
                <h5>{parkinglot.name}</h5>
                <p className="text-muted mb-0">{parkinglot.address}</p>
                {parkinglot.pricePerHour && (
                  <p className="text-primary">
                    Giá: {parkinglot.pricePerHour.toLocaleString("vi-VN")}{" "}
                    VNĐ/giờ
                  </p>
                )}
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Thông báo thành công */}
      {showSuccessAlert && (
        <Row className="mb-3">
          <Col>
            <Alert variant="success" className="text-center">
              <Check /> Đặt chỗ thành công! Chỗ đỗ {selectedSpace.name} đã được
              đặt.
            </Alert>
          </Col>
        </Row>
      )}

      {/* Thống kê và bộ lọc */}
      <Row className="mb-4">
        <Col>
          <Card className="shadow-sm">
            <Card.Body>
              <Row className="text-center">
                <Col>
                  <h5 className="text-primary">{stats.total}</h5>
                  <small>Tổng chỗ</small>
                </Col>
                <Col>
                  <h5 className="text-success">{stats.available}</h5>
                  <small>Còn trống</small>
                </Col>
                <Col>
                  <h5 className="text-warning">{stats.booked}</h5>
                  <small>Đã đặt</small>
                </Col>
                <Col>
                  <h5 className="text-danger">{stats.occupited}</h5>
                  <small>Đang sử dụng</small>
                </Col>
                <Col>
                  <h5 className="text-secondary">{stats.blocked}</h5>
                  <small>Bị chặn</small>
                </Col>
              </Row>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Chú thích */}
      <Row className="mb-3">
        <Col>
          <Card className="shadow-sm">
            <Card.Body>
              <div className="d-flex justify-content-center gap-4 flex-wrap">
                <div className="d-flex align-items-center">
                  <div
                    style={{
                      width: "20px",
                      height: "20px",
                      backgroundColor: "#28a745",
                      marginRight: "8px",
                      borderRadius: "4px",
                    }}
                  ></div>
                  <span>Có thể đặt</span>
                </div>
                <div className="d-flex align-items-center">
                  <div
                    style={{
                      width: "20px",
                      height: "20px",
                      backgroundColor: "#ffc107",
                      marginRight: "8px",
                      borderRadius: "4px",
                    }}
                  ></div>
                  <span>Đã đặt</span>
                </div>
                <div className="d-flex align-items-center">
                  <div
                    style={{
                      width: "20px",
                      height: "20px",
                      backgroundColor: "#dc3545",
                      marginRight: "8px",
                      borderRadius: "4px",
                    }}
                  ></div>
                  <span>Đang sử dụng</span>
                </div>
                <div className="d-flex align-items-center">
                  <div
                    style={{
                      width: "20px",
                      height: "20px",
                      backgroundColor: "#6c757d",
                      marginRight: "8px",
                      borderRadius: "4px",
                    }}
                  ></div>
                  <span>Bị chặn</span>
                </div>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Sơ đồ bãi xe */}
      {parkingSpaces && parkingSpaces.length > 0 ? (
        <>
          <Row>
            <Col>
              <Card className="shadow-sm">
                <Card.Header>
                  <h5 className="mb-0">Sơ đồ bãi xe</h5>
                </Card.Header>
                <Card.Body>
                  <div
                    className="d-flex flex-wrap gap-3 justify-content-center"
                    style={{ padding: "20px" }}
                  >
                    {parkingSpaces.map((slot) => (
                      <div
                        key={slot.id}
                        onClick={() => handleSlotClick(slot)}
                        style={{
                          width: "120px",
                          height: "80px",
                          backgroundColor: getSlotColor(slot.status),
                          border: "2px solid #dee2e6",
                          borderRadius: "8px",
                          display: "flex",
                          flexDirection: "column",
                          alignItems: "center",
                          justifyContent: "center",
                          color: "white",
                          cursor:
                            slot.status === "AVAILABLE"
                              ? "pointer"
                              : "not-allowed",
                          transition: "all 0.3s ease",
                          position: "relative",
                          boxShadow: "0 2px 4px rgba(0,0,0,0.1)",
                        }}
                        className={
                          slot.status === "AVAILABLE" ? "slot-hover" : ""
                        }
                      >
                        <div style={{ fontSize: "24px", marginBottom: "4px" }}>
                          {getVehicleIcon(slot.vehicleType)}
                        </div>
                        <div style={{ fontSize: "14px", fontWeight: "bold" }}>
                          {slot.name}
                        </div>
                        <div style={{ fontSize: "10px", opacity: 0.9 }}>
                          {slot.vehicleType}
                        </div>
                        {slot.status === "AVAILABLE" && (
                          <div
                            style={{
                              position: "absolute",
                              top: "-8px",
                              right: "-8px",
                              backgroundColor: "#ffc107",
                              color: "#000",
                              borderRadius: "50%",
                              width: "20px",
                              height: "20px",
                              display: "flex",
                              alignItems: "center",
                              justifyContent: "center",
                              fontSize: "12px",
                              fontWeight: "bold",
                            }}
                          >
                            ✓
                          </div>
                        )}
                      </div>
                    ))}
                  </div>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </>
      ) : (
        <>
          <Row className="mt-4">
            <Col className="text-center">
              <div className="alert alert-info" role="alert">
                Vui lòng chọn thời gian bắt đầu và kết thúc để hiển thị chỗ đậu
                xe phù hợp.
              </div>
            </Col>
          </Row>
        </>
      )}

      {/* Modal đặt chỗ */}
      <Modal
        show={showBookingModal}
        onHide={() => setShowBookingModal(false)}
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>Đặt chỗ đỗ xe - {selectedSpace?.name}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div
            className="mb-3 p-3"
            style={{ backgroundColor: "#f8f9fa", borderRadius: "8px" }}
          >
            <div className="d-flex justify-content-between align-items-center">
              <div>
                <strong>{selectedSpace.name}</strong>
                <div className="text-muted">
                  {getVehicleIcon(selectedSpace.vehicleType)}
                  {selectedSpace.vehicleType}
                </div>
              </div>
              <Badge bg="success">Có thể đặt</Badge>
            </div>
          </div>

          <Form.Group className="mb-3">
            <Form.Label>Biển số xe</Form.Label>
            <Form.Control
              type="text"
              value={bookingForm.vehiclePlate}
              onChange={(e) =>
                setBookingForm({
                  ...bookingForm,
                  vehiclePlate: e.target.value,
                })
              }
              placeholder="Nhập biển số xe (Có thể để trống)"
              required
            />
          </Form.Group>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Giờ bắt đầu</Form.Label>
              <Form.Control
                type="datetime-local"
                value={startTime}
                onChange={(e) =>
                  setBookingForm({
                    ...bookingForm,
                    startTime: e.target.value,
                  })
                }
                disabled={true}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Giờ kết thúc</Form.Label>
              <Form.Control
                type="datetime-local"
                value={endTime}
                onChange={(e) =>
                  setBookingForm({
                    ...bookingForm,
                    endTime: e.target.value,
                  })
                }
                disabled={true}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Số giờ đậu</Form.Label>
              <Form.Control
                type="text"
                value={getHourDifference(startTime, endTime)} // <-- Gọi hàm tính số giờ
                disabled={true}
              />
            </Form.Group>
            {parkinglot.pricePerHour && (
              <div className="alert alert-info">
                <strong>Tổng tiền: </strong>
                {parkinglot?.pricePerHour && bookingForm.hours
                  ? (
                      Number(parkinglot.pricePerHour) *
                      Number(bookingForm.hours)
                    ).toLocaleString("vi-VN") + " đ"
                  : "Chưa xác định"}
              </div>
            )}
          </Form>
        </Modal.Body>
        <Modal.Footer>
          {user === null ? (
            <>
              <div className="text-center">
                <p className="text-muted mb-0">
                  Vui lòng <Link to={"/login?next=/spaces"}>đăng nhập</Link> để
                  đặt chỗ
                </p>
              </div>
            </>
          ) : (
            <>
              <Button
                variant="secondary"
                onClick={() => setShowBookingModal(false)}
              >
                Hủy
              </Button>
              <Button variant="primary" onClick={Booking}>
                Xác nhận đặt chỗ
              </Button>
            </>
          )}
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default ParkingBookingSystem;
