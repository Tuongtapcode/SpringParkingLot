import React, { useState, useEffect, useContext } from "react";
import {
  Container,
  Row,
  Col,
  Card,
  Button,
  Badge,
  Alert,
  Modal,
  Spinner,
  Form,
} from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { authApis, endpoints } from "../../configs/Apis";
import MySpinner from "../layout/MySpinner";
import { MyUserContext } from "../../configs/Contexts";
import AlertNoUser from "../layout/AlertNoUser";

const Reservation = () => {
  const [reservations, setReservations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [modalData, setModalData] = useState({
    selectedIds: [],
  });
  const [processing, setProcessing] = useState(false);
  const [selectedReservations, setSelectedReservations] = useState(new Set());
  const [isMultiSelectMode, setIsMultiSelectMode] = useState(false);
  const [paymentResult, setPaymentResult] = useState(null);
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [user] = useContext(MyUserContext);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);
  const loadReservation = async () => {
    if (!hasMore) return;
    let url = `${endpoints["upcoming"]}?page=${page}`;
    try {
      setLoading(true);
      let res = await authApis().get(url);
      console.log(res.data);
      if (res.data.length === 0 && page > 1) {
        setHasMore(false);
      } else {
        if (page <= 1) setReservations(res.data);
        else setReservations([...reservations, ...res.data]);
      }
    } catch (error) {
      console.error("Error loading:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadReservation();
  }, [page]);

  const loadMore = () => {
    if (hasMore) {
      setPage(page + 1);
    }
  };

  const pays = async () => {
    setProcessing(true);
    try {
      const selectedIds = modalData.selectedIds;
      const requestBody = selectedIds.map((id) => ({
        reservationId: id,
        method: "VNPAY",
      }));
      let res = await authApis().post(endpoints["pays"], requestBody);
      if (res.status == 201) {
        setPaymentResult(res.data);
        setShowSuccessModal(true);
      }
    } catch (error) {
      console.error("Lỗi thanh toán:", error);
    } finally {
      setProcessing(false);
      setShowModal(false);
      setModalData({ selectedIds: [] });
      setSelectedReservations(new Set());
      setHasMore(true);
      setReservations([]);
      setPage(1);
      loadReservation();
    }
  };
  const CancelReservation = async (reservationId) => {
    try {
      if (window.confirm("Bạn có chắc chắn muốn hủy đặt chỗ này?")) {
        let res = await authApis().post(
          `${endpoints["reservation"]}/${reservationId}/cancel`
        );
        console.log(res.status);
        if (res.status === 200) {
          alert("Đã hủy thành công!");
        } else {
          alert("Hủy không thành công, Vui lòng thử lại sau!");
        }
      }
    } catch (err) {
      console.error("Lỗi khi hủy đặt chỗ:", err);
      alert("Có lỗi xảy ra khi hủy đặt chỗ.");
    } finally {
      setHasMore(true);
      setReservations([]);
      setPage(1);
      loadReservation();
    }
  };
  const formatDateTime = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleString("vi-VN", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  const handlePayment = (reservationId) => {
    let selectedIds;
    if (reservationId === undefined || reservationId === null) {
      selectedIds = Array.from(selectedReservations);
    } else {
      selectedIds = [reservationId];
    }
    setModalData({
      selectedIds,
    });
    setShowModal(true);
  };

  const handleSelectReservation = (reservationId) => {
    const newSelected = new Set(selectedReservations);
    if (newSelected.has(reservationId)) {
      newSelected.delete(reservationId);
    } else {
      newSelected.add(reservationId);
    }
    setSelectedReservations(newSelected);
  };

  const handleSelectAll = () => {
    const pendingReservations = reservations.filter(
      (res) => res.status === "PENDING"
    );
    if (selectedReservations.size === pendingReservations.length) {
      setSelectedReservations(new Set());
    } else {
      setSelectedReservations(
        new Set(pendingReservations.map((res) => res.id))
      );
    }
  };

  const toggleMultiSelectMode = () => {
    setIsMultiSelectMode(!isMultiSelectMode);
    setSelectedReservations(new Set());
  };

  const getStatusBadge = (status) => {
    const statusMap = {
      PENDING: { variant: "warning", text: "Đang chờ" },
      CONFIRM: { variant: "success", text: "Đã xác nhận" },
      CANCELLED: { variant: "danger", text: "Đã hủy" },
      COMPLETED: { variant: "primary", text: "Hoàn thành" },
      EXPIRED: { variant: "dark", text: "Hết hạn" },
    };

    const statusInfo = statusMap[status] || {
      variant: "secondary",
      text: status,
    };
    return <Badge bg={statusInfo.variant}>{statusInfo.text}</Badge>;
  };
  const formatCurrency = (amount) => {
    return new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND",
      currencyDisplay: "code",
      maximumFractionDigits: 0,
    }).format(Math.floor(amount));
  };

  const calculateTotalAmount = (reservationIds) => {
    return reservations
      .filter((res) => reservationIds.includes(res.id))
      .reduce((total, res) => {
        return total + (res.baseAmount + res.extraFee - res.discount);
      }, 0);
  };

  const pendingReservations = reservations.filter(
    (res) => res.status === "PENDING"
  );
  if (user === null) {
    return (
      <>
        <AlertNoUser title="đặt chỗ" urlNext="/login?next=/bookings" />
      </>
    );
  }

  return (
    <Container className="py-4">
      <Row>
        <Col>
          <div className="d-flex justify-content-between align-items-center mb-4">
            <h2>
              <i className="bi bi-car-front me-2"></i>
              Danh Sách Đặt Chỗ Đang Chờ Xử Lý
            </h2>

            {pendingReservations.length > 1 && (
              <div className="d-flex gap-2">
                <Button
                  variant={
                    isMultiSelectMode ? "outline-secondary" : "outline-primary"
                  }
                  onClick={toggleMultiSelectMode}
                  size="sm"
                >
                  <i
                    className={`bi ${
                      isMultiSelectMode ? "bi-x-lg" : "bi-check2-square"
                    } me-1`}
                  ></i>
                  {isMultiSelectMode ? "Thoát chế độ chọn" : "Chọn nhiều"}
                </Button>
              </div>
            )}
          </div>

          {/* Multi-select controls */}
          {isMultiSelectMode && pendingReservations.length > 0 && (
            <Alert variant="info" className="mb-4">
              <div className="d-flex justify-content-between align-items-center">
                <div>
                  <Form.Check
                    type="checkbox"
                    id="select-all"
                    label={`Chọn tất cả (${selectedReservations.size}/${pendingReservations.length})`}
                    checked={
                      selectedReservations.size === pendingReservations.length
                    }
                    onChange={handleSelectAll}
                    className="mb-0"
                  />
                  {selectedReservations.size > 0 && (
                    <small className="text-muted">
                      Tổng tiền:{" "}
                      <strong>
                        {calculateTotalAmount(
                          Array.from(selectedReservations)
                        ).toLocaleString("vi-VN")}{" "}
                        VNĐ
                      </strong>
                    </small>
                  )}
                </div>
                <div></div>
                <Button
                  variant="primary"
                  onClick={() => handlePayment()}
                  disabled={selectedReservations.size === 0}
                  size="sm"
                >
                  <i className="bi bi-credit-card me-1"></i>
                  Thanh toán ({selectedReservations.size})
                </Button>
              </div>
            </Alert>
          )}

          {reservations.length === 0 ? (
            <Alert variant="info">
              <Alert.Heading>Không có đặt chỗ nào</Alert.Heading>
              <p>Hiện tại bạn không có đặt chỗ nào đang chờ xử lý.</p>
            </Alert>
          ) : (
            <Row>
              {reservations.map((reservation) => (
                <Col key={reservation.id} md={6} lg={4} className="mb-4">
                  <Card
                    className={`h-100 shadow-sm ${
                      isMultiSelectMode &&
                      selectedReservations.has(reservation.id)
                        ? "border-primary"
                        : ""
                    }`}
                    style={{
                      backgroundColor:
                        isMultiSelectMode &&
                        selectedReservations.has(reservation.id)
                          ? "#f8f9ff"
                          : "white",
                    }}
                  >
                    <Card.Header className="d-flex justify-content-between align-items-center">
                      <div className="d-flex align-items-center">
                        {isMultiSelectMode &&
                          reservation.status === "PENDING" && (
                            <Form.Check
                              type="checkbox"
                              id={`reservation-${reservation.id}`}
                              checked={selectedReservations.has(reservation.id)}
                              onChange={() =>
                                handleSelectReservation(reservation.id)
                              }
                              className="me-2"
                            />
                          )}
                        <strong>Đặt chỗ #{reservation.id}</strong>
                      </div>
                      {getStatusBadge(reservation.status)}
                    </Card.Header>

                    <Card.Body>
                      <div className="mb-3">
                        <h6 className="text-muted mb-1">Tên bãi đỗ:</h6>
                        <p className="mb-2">
                          <i className="bi bi-geo-alt me-1"></i>
                          {reservation.parkingSpaceId?.parkingLotId?.name ||
                            "N/A"}
                        </p>
                      </div>
                      <div className="mb-3">
                        <h6 className="text-muted mb-1">Địa chỉ:</h6>
                        <p className="mb-2">
                          <i className="bi bi-geo-alt me-1"></i>
                          {reservation.parkingSpaceId?.parkingLotId?.address ||
                            "N/A"}
                        </p>
                      </div>
                      <div className="mb-3">
                        <h6 className="text-muted mb-1">Tên chỗ đỗ:</h6>
                        <p className="mb-2">
                          <i className="bi bi-geo-alt me-1"></i>
                          {reservation.parkingSpaceId?.name || "N/A"}
                        </p>
                      </div>

                      <div className="mb-3">
                        <h6 className="text-muted mb-1">Phương tiện:</h6>
                        <p className="mb-2">
                          <i className="bi bi-car-front me-1"></i>
                          {reservation.parkingSpaceId?.vehicleType || "N/A"}
                        </p>
                      </div>

                      <div className="mb-3">
                        <h6 className="text-muted mb-1">Bắt đầu:</h6>
                        <small className="d-block">
                          {formatDateTime(reservation.startTime)}
                        </small>
                      </div>
                      <div className="mb-3">
                        <h6 className="text-muted mb-1">Kết thúc:</h6>
                        <small className="d-block">
                          {formatDateTime(reservation.endTime)}
                        </small>
                      </div>
                      <div className="mb-3">
                        <h6 className="text-muted mb-1">Chi phí:</h6>
                        <p className="mb-0">
                          <strong className="text-success">
                            {formatCurrency(
                              (reservation.baseAmount || 0) +
                                (reservation.extraFee || 0) -
                                (reservation.discount || 0)
                            )}
                          </strong>
                        </p>
                      </div>

                      <small className="text-muted">
                        Đặt lúc: {formatDateTime(reservation.createdAt)}
                      </small>
                    </Card.Body>

                    {reservation.status === "PENDING" && !isMultiSelectMode && (
                      <Card.Footer className="bg-transparent">
                        <div className="d-grid gap-2 d-md-flex justify-content-md-end">
                          <Button
                            variant="danger"
                            size="sm"
                            onClick={() => CancelReservation(reservation.id)}
                          >
                            <i className="bi bi-credit-card me-1"></i>
                            Hủy
                          </Button>
                          <Button
                            variant="primary"
                            size="sm"
                            onClick={() => handlePayment(reservation.id)}
                          >
                            <i className="bi bi-credit-card me-1"></i>
                            Thanh toán
                          </Button>
                        </div>
                      </Card.Footer>
                    )}
                    {reservation.status === "COMPLETED" &&
                      !isMultiSelectMode && (
                        <Card.Footer className="bg-transparent">
                          <div className="d-grid gap-2 d-md-flex justify-content-md-end">
                            <Button
                              variant="primary"
                              size="sm"
                              onClick={() => handlePayment(reservation.id)}
                            >
                              <i className="bi bi-credit-card me-1"></i>
                              Đánh giá dịch vụ
                            </Button>
                          </div>
                        </Card.Footer>
                      )}
                  </Card>
                </Col>
              ))}
              <div className="d-flex gap-2 justify-content-center my-3">
                {page > 0 && (
                  <div className="mt-2 mb-2 text-center">
                    <Button variant="primary" onClick={loadMore}>
                      Xem thêm...
                    </Button>
                  </div>
                )}
              </div>
              {loading && <MySpinner />}
            </Row>
          )}
        </Col>
      </Row>

      <Modal
        show={showSuccessModal}
        onHide={() => setShowSuccessModal(false)}
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>Thanh toán thành công!</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {paymentResult && (
            <div>
              <p>
                <strong>Mã giao dịch:</strong> {paymentResult.id}
              </p>
              <p>
                <strong>Phương thức:</strong> {paymentResult.method}
              </p>
              <p>
                <strong>Số tiền:</strong> {formatCurrency(paymentResult.amount)}{" "}
                VNĐ
              </p>
              <p>
                <strong>Mô tả:</strong> {paymentResult.description}
              </p>
              <h6>Danh sách đặt chỗ:</h6>
              <ul>
                {paymentResult.reservationSet?.map((r) => (
                  <li key={r.id}>
                    Đặt chỗ #{r.id} - {r.parkingSpaceId?.name} (
                    {r.parkingSpaceId?.vehicleType})
                  </li>
                ))}
              </ul>
            </div>
          )}
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant="primary"
            onClick={() => {
              setShowSuccessModal(false);
            }}
          >
            Đóng
          </Button>
        </Modal.Footer>
      </Modal>

      {/* Modal xác nhận */}
      <Modal show={showModal} onHide={() => setShowModal(false)} centered>
        <Modal.Header closeButton>
          <Modal.Title>Xác nhận thanh toán</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div>
            <p>
              {`Bạn có chắc chắn muốn thanh toán cho ${modalData.selectedIds.length} đặt chỗ?`}
            </p>

            <div className="mt-3 p-3 bg-light rounded">
              <h6>Chi tiết:</h6>
              <ul className="mb-2">
                {modalData.selectedIds.map((id) => (
                  <li key={id}>Đặt chỗ #{id}</li>
                ))}
              </ul>
              <strong>
                Tổng tiền:{" "}
                {formatCurrency(calculateTotalAmount(modalData.selectedIds))}
              </strong>
            </div>
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant="secondary"
            onClick={() => setShowModal(false)}
            disabled={processing}
          >
            Đóng
          </Button>
          <Button variant="primary" onClick={pays} disabled={processing}>
            {processing ? (
              <>
                <Spinner animation="border" size="sm" className="me-2" />
                Đang xử lý...
              </>
            ) : (
              "Thanh toán"
            )}
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default Reservation;
