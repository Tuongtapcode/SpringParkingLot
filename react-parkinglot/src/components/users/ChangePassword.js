import { Lock, Shield, Eye, EyeOff, Save, ArrowLeft } from "lucide-react";
import { useState, useEffect, useContext } from "react";
import {
  Alert,
  Button,
  Card,
  Col,
  Container,
  Form,
  Row,
} from "react-bootstrap";
import { useNavigate, Link } from "react-router-dom";
import { MyUserContext } from "../../configs/Contexts";
import { authApis, endpoints } from "../../configs/Apis";

const ChangePassword = () => {
  const [currentUser, dispatch] = useContext(MyUserContext);
  const passwordFields = [
    {
      title: "Mật khẩu hiện tại",
      field: "currentPassword",
      icon: <Lock size={16} />,
    },
    {
      title: "Mật khẩu mới",
      field: "newPassword",
      icon: <Shield size={16} />,
    },
    {
      title: "Xác nhận mật khẩu mới",
      field: "confirmPassword",
      icon: <Shield size={16} />,
    },
  ];

  const [passwords, setPasswords] = useState({
    currentPassword: "",
    newPassword: "",
    confirmPassword: "",
  });
  const [msg, setMsg] = useState("");
  const [msgType, setMsgType] = useState("danger");
  const [loading, setLoading] = useState(false);
  const [showPasswords, setShowPasswords] = useState({
    currentPassword: false,
    newPassword: false,
    confirmPassword: false,
  });
  const nav = useNavigate();

  useEffect(() => {
    if (!currentUser) {
      nav("/login?next=/settings/changepassword");
    }
  }, [currentUser, nav]);

  const togglePasswordVisibility = (field) => {
    setShowPasswords((prev) => ({
      ...prev,
      [field]: !prev[field],
    }));
  };

  const validatePasswords = () => {
    if (
      !passwords.currentPassword ||
      !passwords.newPassword ||
      !passwords.confirmPassword
    ) {
      setMsg("Vui lòng điền đầy đủ thông tin!");
      setMsgType("danger");
      return false;
    }

    if (passwords.newPassword !== passwords.confirmPassword) {
      setMsg("Mật khẩu mới và xác nhận mật khẩu không khớp!");
      setMsgType("danger");
      return false;
    }

    // Check if new password is different from current password
    if (passwords.currentPassword === passwords.newPassword) {
      setMsg("Mật khẩu mới phải khác với mật khẩu hiện tại!");
      setMsgType("danger");
      return false;
    }

    setMsg("");
    return true;
  };

  const changePassword = async (event) => {
    event.preventDefault();

    if (!validatePasswords()) {
      return;
    }

    try {
      setLoading(true);

      const formData = new FormData();
      if (passwords.newPassword && passwords.currentPassword) {
        formData.append("password", passwords.newPassword);
        formData.append("currentPassword", passwords.currentPassword);
      }
      let res = await authApis().put(endpoints["profile"], formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      if (res.status === 200) {
        console.log(res.data);
        setMsg("Đổi mật khẩu thành công!");
        setMsgType("success");

        // Clear form after successful change
        setPasswords({
          currentPassword: "",
          newPassword: "",
          confirmPassword: "",
        });

        // Redirect to profile after 2 seconds
        dispatch({ type: "logout" });
        setTimeout(() => {
          nav("/login");
        }, 2000);
      }
    } catch (ex) {
      console.error(ex);
      if (ex.response && ex.response.status === 400) {
        setMsg("Mật khẩu hiện tại không đúng!");
      } else if (ex.response && ex.response.status === 401) {
        setMsg("Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại!");
        setTimeout(() => {
          nav("/login");
        }, 2000);
      } else {
        setMsg("Đổi mật khẩu thất bại. Vui lòng thử lại!");
      }
      setMsgType("danger");
    } finally {
      setLoading(false);
    }
  };

  if (!currentUser) {
    return null;
  }

  return (
    <>
      <div className="bg-light min-vh-100 py-5">
        <Container>
          <Row className="justify-content-center">
            <Col md={8} lg={6}>
              <Card className="shadow-lg border-0">
                <Card.Header className="bg-dark text-white text-center py-4">
                  <div className="d-flex align-items-center justify-content-center mb-2">
                    <Shield size={32} className="me-2" />
                    <h3 className="mb-0 fw-bold">ĐỔI MẬT KHẨU</h3>
                  </div>
                  <p className="mb-0 opacity-75">
                    Cập nhật mật khẩu bảo mật cho tài khoản của bạn
                  </p>
                </Card.Header>

                <Card.Body className="p-4">
                  {msg && (
                    <Alert variant={msgType} className="mb-4">
                      {msg}
                    </Alert>
                  )}

                  <Form onSubmit={changePassword}>
                    <Row>
                      {passwordFields.map((field) => (
                        <Col md={12} key={field.field} className="mb-3">
                          <Form.Group controlId={field.field}>
                            <Form.Label className="fw-medium d-flex align-items-center">
                              {field.icon}
                              <span className="ms-2">{field.title}</span>
                            </Form.Label>
                            <div className="position-relative">
                              <Form.Control
                                required
                                value={passwords[field.field]}
                                onChange={(e) =>
                                  setPasswords({
                                    ...passwords,
                                    [field.field]: e.target.value,
                                  })
                                }
                                type={
                                  showPasswords[field.field]
                                    ? "text"
                                    : "password"
                                }
                                placeholder={`Nhập ${field.title.toLowerCase()}`}
                                className="py-2 pe-5"
                              />
                              <Button
                                variant="link"
                                className="position-absolute end-0 top-50 translate-middle-y border-0 text-muted p-2"
                                onClick={() =>
                                  togglePasswordVisibility(field.field)
                                }
                                type="button"
                                style={{ zIndex: 5 }}
                              >
                                {showPasswords[field.field] ? (
                                  <EyeOff size={16} />
                                ) : (
                                  <Eye size={16} />
                                )}
                              </Button>
                            </div>
                          </Form.Group>
                        </Col>
                      ))}
                    </Row>

                    <div className="d-grid gap-2 mt-4">
                      {loading ? (
                        <Button
                          variant="primary"
                          disabled
                          size="lg"
                          className="py-3"
                        >
                          <span
                            className="spinner-border spinner-border-sm me-2"
                            role="status"
                            aria-hidden="true"
                          ></span>
                          Đang cập nhật...
                        </Button>
                      ) : (
                        <Button
                          type="submit"
                          variant="dark"
                          size="lg"
                          className="py-3"
                        >
                          <Save size={20} className="me-2" />
                          Đổi mật khẩu
                        </Button>
                      )}
                    </div>
                  </Form>

                  <hr className="my-4" />

                  <div className="d-flex justify-content-between align-items-center">
                    <Link
                      to="/profile"
                      className="btn btn-outline-secondary d-flex align-items-center"
                    >
                      <ArrowLeft size={16} className="me-2" />
                      Quay lại Profile
                    </Link>

                    <div className="text-center">
                      <span className="text-muted">Tài khoản: </span>
                      <span className="fw-medium">{currentUser.username}</span>
                    </div>
                  </div>

                  {/* Security Tips */}
                  <Card className="mt-4 bg-light border-0">
                    <Card.Body className="p-3">
                      <h6 className="mb-2 text-primary">
                        <Shield size={16} className="me-2" />
                        Lời khuyên bảo mật
                      </h6>
                      <ul className="mb-0 small text-muted">
                        <li>Sử dụng mật khẩu mạnh với ít nhất 8 ký tự</li>
                        <li>
                          Kết hợp chữ hoa, chữ thường, số và ký tự đặc biệt
                        </li>
                        <li>Không chia sẻ mật khẩu với bất kỳ ai</li>
                        <li>Đổi mật khẩu định kỳ để tăng cường bảo mật</li>
                      </ul>
                    </Card.Body>
                  </Card>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </Container>
      </div>
    </>
  );
};

export default ChangePassword;
