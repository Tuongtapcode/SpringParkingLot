import { useState, useEffect } from "react";
import {
  Container,
  Row,
  Col,
  Card,
  Button,
  Badge,
  Form,
  InputGroup,
  Alert,
  OverlayTrigger,
  Tooltip,
} from "react-bootstrap";
import { CircleParking, Filter, MapPin, Search, Star, Tag } from "lucide-react";
import Apis, { endpoints } from "../../configs/Apis";
import { Link } from "react-router-dom";
import MySpinner from "../layout/MySpinner";
const ParkingLots = () => {
  const [parkingLots, setParkingLots] = useState([]);
  const [hasMore, setHasMore] = useState(true);
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(1);
  const [kw, setKw] = useState();
  const [address, setAddress] = useState();
  const [orderBy, setOrderBy] = useState();

  const loadParkingLots = async () => {
    if (!hasMore) return;
    let url = `${endpoints["parkingLots"]}?page=${page}`;
    if (kw) url = `${url}&kw=${kw}`;
    if (address) url = `${url}&address=${address}`;
    if (orderBy) url = `${url}&orderBy=${orderBy}`;
    console.log(url);
    try {
      setLoading(true);
      let res = await Apis.get(url);
      console.log(res.data);
      if (res.data.length === 0 && page > 1) {
        setHasMore(false);
      } else {
        if (page <= 1) setParkingLots(res.data);
        else setParkingLots([...parkingLots, ...res.data]);
      }
    } catch (error) {
      console.error("Error loading parkingLots:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    let timer = setTimeout(() => {
      if (page > 0) {
        setLoading(true);
        loadParkingLots();
      }
    }, 500);
    return () => clearTimeout(timer);
  }, [page, kw, address, orderBy]);

  useEffect(() => {
    setPage(1);
  }, [kw, address, orderBy]);

  const loadMore = () => {
    if (hasMore) {
      setPage(page + 1);
    }
  };

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND",
    }).format(amount);
  };

  const getStatusBadge = (isActive) => {
    if (isActive) return <Badge bg="success">Hoạt động</Badge>;
    else return <Badge bg="danger">Tạm ngừng</Badge>;
  };

  // Component hiển thị tiện ích
  const AmenityBadge = ({ amenity }) => (
    <OverlayTrigger
      placement="top"
      overlay={
        <Tooltip id={`tooltip-${amenity.id}`}>{amenity.description}</Tooltip>
      }
    >
      <Badge
        bg="light"
        text="dark"
        className="me-1 mb-1 d-inline-flex align-items-center"
        style={{ cursor: "pointer", fontSize: "0.75rem" }}
      >
        <img
          src={amenity.image}
          alt="Avatar"
          width={32}
          height={16}
          style={{ objectFit: "cover" }}
        />
        <span className="ms-1">{amenity.name}</span>
      </Badge>
    </OverlayTrigger>
  );

  return (
    <Container
      fluid
      className="py-4"
      style={{ backgroundColor: "#f8f9fa", minHeight: "100vh" }}
    >
      <Row className="mb-4">
        <Col>
          <h2
            className="text-center mb-4"
            style={{ color: "#2c3e50", fontWeight: "bold" }}
          >
            <Search /> Tìm kiếm & Đặt bãi đỗ xe <CircleParking />
          </h2>
        </Col>
      </Row>
      <Card className="mb-4 shadow-sm">
        <Card.Body>
          <Row>
            <Col md={6} lg={4} className="mb-3">
              <Form.Label>Tên bãi đỗ</Form.Label>
              <InputGroup>
                <InputGroup.Text>
                  <Tag size={16} />
                </InputGroup.Text>
                <Form.Control
                  type="text"
                  placeholder="Tìm theo tên..."
                  value={kw || ""}
                  onChange={(e) => setKw(e.target.value)}
                />
              </InputGroup>
            </Col>

            <Col md={6} lg={4} className="mb-3">
              <Form.Label>Địa chỉ bãi đỗ</Form.Label>
              <InputGroup>
                <InputGroup.Text>
                  <MapPin size={16} />
                </InputGroup.Text>
                <Form.Control
                  type="text"
                  placeholder="Tìm theo địa chỉ..."
                  value={address || ""}
                  onChange={(e) => setAddress(e.target.value)}
                />
              </InputGroup>
            </Col>

            <Col md={6} lg={3} className="mb-3">
              <Form.Label>Sắp xếp theo</Form.Label>
              <InputGroup>
                <InputGroup.Text>
                  <Filter size={16} />
                </InputGroup.Text>

                <Form.Select
                  value={orderBy}
                  onChange={(e) => setOrderBy(e.target.value)}
                >
                  <option value="pricePerHour">Giá tiền</option>
                  <option value="name">Tên</option>
                  <option value="rating">Đánh giá</option>
                  <option value="availability">Chỗ trống</option>
                  <option value="distance">Khoảng cách</option>
                </Form.Select>
              </InputGroup>
            </Col>

            <Col md={6} lg={1} className="mb-3 d-flex align-items-end">
              <Button
                variant="primary"
                className="w-100"
                onClick={() => {
                  loadParkingLots();
                }}
              >
                <Search size={16} />
              </Button>
            </Col>
          </Row>
        </Card.Body>
      </Card>

      <Row>
        <Col md={12}>
          <h5 className="mb-3">Chọn bãi đỗ xe</h5>
          {(!parkingLots || parkingLots.length === 0) && (
            <Alert variant="info" className="mt-2">
              Không có bãi đỗ xe nào!
            </Alert>
          )}

          {parkingLots.map((parkingLot) => (
            <Card key={parkingLot.id} className="mb-3 shadow-sm">
              <Link
                to={`/spaces?pklId=${parkingLot.id}`}
                className="text-decoration-none text-dark"
              >
                <Card.Body>
                  <Row>
                    <Col md={2}>
                      <Card.Img variant="top" src={parkingLot.image} />
                    </Col>
                    <Col md={4}>
                      <Row>
                        <Col>
                          <h5 className="mb-2">{parkingLot.name}</h5>
                          <p className="text-muted mb-2">
                            <MapPin size={14} className="me-1" />
                            {parkingLot.address}
                          </p>
                          <p className="mb-2">{parkingLot.description}</p>
                          {/* Hiển thị tiện ích */}
                          {parkingLot.extensionSet &&
                            parkingLot.extensionSet.length > 0 && (
                              <div className="mb-2">
                                <small className="text-muted d-block mb-1">
                                  Tiện ích:
                                </small>
                                <div className="d-flex flex-wrap">
                                  {parkingLot.extensionSet.map((amenity) => (
                                    <AmenityBadge
                                      key={amenity.id}
                                      amenity={amenity}
                                    />
                                  ))}
                                </div>
                              </div>
                            )}

                          <div className="d-flex align-items-center">
                            <div className="me-3">
                              <Star size={14} className="text-warning me-1" />
                              <span>/5</span>
                            </div>
                            <div className="me-3">
                              <MapPin size={14} className="text-primary me-1" />
                              <span> km</span>
                            </div>
                            <div>{getStatusBadge(parkingLot.isActive)}</div>
                          </div>
                        </Col>
                      </Row>
                    </Col>

                    <Col md={6} className="text-end">
                      <h4 className="text-primary mb-2">
                        {formatCurrency(parkingLot.pricePerHour)}/giờ
                      </h4>
                      <h5 className="text-success mb-2">
                        Tổng chỗ: {parkingLot.totalSlots}
                      </h5>
                      <p className="mb-2">Còn trống: 100 chỗ</p>

                      <Button variant="primary" size="lg">
                        Đặt chỗ ngay
                      </Button>
                    </Col>
                  </Row>
                </Card.Body>
              </Link>
            </Card>
          ))}
        </Col>
        {loading && <MySpinner />}
        {page > 0 && (
          <div className="mt-2 mb-2 text-center">
            <Button variant="primary" onClick={loadMore}>
              Xem thêm...
            </Button>
          </div>
        )}
      </Row>
    </Container>
  );
};

export default ParkingLots;
