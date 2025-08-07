import { useState, useEffect, useContext } from "react";
import { authApis, endpoints } from "../../configs/Apis";
import { Button } from "react-bootstrap";
import MySpinner from "../layout/MySpinner";
import { MyUserContext } from "../../configs/Contexts";
import AlertNoUser from "../layout/AlertNoUser";

const Payment = () => {
  const [user] = useContext(MyUserContext);
  const [payments, setPayments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);

  const loadPayments = async () => {
    if (!hasMore) return;
    try {
      setLoading(true);
      let url = `${endpoints["pays"]}?page=${page}`;
      setLoading(true);
      let res = await authApis().get(url);
      console.log(res.data);
      if (res.data.length === 0 && page > 1) {
        setHasMore(false);
      } else {
        if (page <= 1) setPayments(res.data);
        else setPayments([...payments, ...res.data]);
      }
    } catch (error) {
      console.error("Error loading:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadPayments(page);
  }, [page]);

  const loadMore = () => {
    if (hasMore) {
      setPage(page + 1);
    }
  };

  // Hàm format số tiền
  const formatCurrency = (amount) => {
    return new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND",
    }).format(amount);
  };

  // Hàm format ngày tháng
  const formatDate = (timestamp) => {
    return new Date(timestamp).toLocaleString("vi-VN", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  // Hàm lấy màu badge theo status
  const getStatusBadge = (status) => {
    switch (status) {
      case "COMPLETED":
        return <span className="badge bg-success">Thành công</span>;
      case "PENDING":
        return <span className="badge bg-warning text-dark">Đang xử lý</span>;
      case "FAILED":
        return <span className="badge bg-danger">Thất bại</span>;
      default:
        return <span className="badge bg-secondary">Không xác định</span>;
    }
  };

  // Hàm lấy tên phương thức thanh toán
  const getPaymentMethodName = (method) => {
    switch (method) {
      case "VNPAY":
        return "VNPay";
      case "MOMO":
        return "MoMo";
      case "BANK_TRANSFER":
        return "Chuyển khoản ngân hàng";
      default:
        return method;
    }
  };
  if (user === null) {
    return (
      <>
        <AlertNoUser title="thanh toán" urlNext="/login?next=/payments" />
      </>
    );
  }
  return (
    <div className="container mt-4">
      <link
        href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css"
        rel="stylesheet"
      />

      <div className="row">
        <div className="col">
          <div className="card">
            <div className="card-header">
              <h4 className="mb-0">Lịch sử thanh toán</h4>
            </div>
            <div className="card-body">
              {payments.length === 0 ? (
                <div className="alert alert-info" role="alert">
                  Chưa có giao dịch thanh toán nào.
                </div>
              ) : (
                <>
                  {/* Hiển thị trên desktop */}
                  <div className="d-none d-md-block">
                    <div className="table-responsive">
                      <table className="table table-striped table-hover">
                        <thead>
                          <tr>
                            <th>ID</th>
                            <th>Phương thức</th>
                            <th>Số tiền</th>
                            <th>Mô tả</th>
                            <th>Mã giao dịch</th>
                            <th>Trạng thái</th>
                            <th>Ngày tạo</th>
                          </tr>
                        </thead>
                        <tbody>
                          {payments.map((payment) => (
                            <tr key={payment.id}>
                              <td>{payment.id}</td>
                              <td>{getPaymentMethodName(payment.method)}</td>
                              <td className="fw-bold text-primary">
                                {formatCurrency(payment.amount)}
                              </td>
                              <td>{payment.description}</td>
                              <td>
                                <small className="text-muted font-monospace">
                                  {payment.transactionId}
                                </small>
                              </td>
                              <td>{getStatusBadge(payment.status)}</td>
                              <td>{formatDate(payment.createdAt)}</td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </div>
                  </div>

                  {/* Hiển thị trên mobile */}
                  <div className="d-md-none">
                    {payments.map((payment) => (
                      <div key={payment.id} className="card mb-3">
                        <div className="card-body">
                          <div className="row">
                            <div className="col">
                              <h6 className="mb-2">
                                {getPaymentMethodName(payment.method)}
                                <span className="ms-2">
                                  {getStatusBadge(payment.status)}
                                </span>
                              </h6>
                              <p className="mb-1">
                                <strong>Số tiền:</strong>{" "}
                                <span className="text-primary fw-bold">
                                  {formatCurrency(payment.amount)}
                                </span>
                              </p>
                              <p className="mb-1">
                                <strong>Mô tả:</strong> {payment.description}
                              </p>
                              <p className="mb-1">
                                <strong>Mã GD:</strong>{" "}
                                <small className="text-muted font-monospace">
                                  {payment.transactionId}
                                </small>
                              </p>
                              <p className="mb-0">
                                <strong>Ngày:</strong>{" "}
                                {formatDate(payment.createdAt)}
                              </p>
                            </div>
                          </div>
                        </div>
                      </div>
                    ))}
                  </div>
                  {loading && <MySpinner />}
                  {/* Phân trang */}
                  <div className="d-flex gap-2 justify-content-center my-3">
                    {page > 0 && (
                      <div className="mt-2 mb-2 text-center">
                        <Button variant="primary" onClick={loadMore}>
                          Xem thêm...
                        </Button>
                      </div>
                    )}
                  </div>
                </>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Payment;
