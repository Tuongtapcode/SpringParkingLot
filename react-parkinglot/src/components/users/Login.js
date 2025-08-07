import { Car, Lock, LogIn, User } from "lucide-react";
import { useContext, useState } from "react";
import {
  Alert,
  Button,
  Card,
  Col,
  Container,
  Form,
  Row,
} from "react-bootstrap";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import cookie from "react-cookies";
import { MyUserContext } from "../../configs/Contexts";
import Apis, { authApis, endpoints } from "../../configs/Apis";

const Login = () => {
  const [user, setUser] = useState({});
  const [msg, setMsg] = useState();
  const [loading, setLoading] = useState(false);
  const nav = useNavigate();
  const [, dispatch] = useContext(MyUserContext);
  const [q] = useSearchParams();
  const login = async (event) => {
    event.preventDefault();
    try {
      setLoading(true);
      console.log(endpoints["login"]);
      let res = await Apis.post(endpoints["login"], {
        ...user,
      });

      if (res.status === 200) {
        console.info(res.data);
        cookie.save("token", res.data.token);
      }
      let u = await authApis().get(endpoints["profile"]);
      console.info(u.data);
      dispatch({
        type: "login",
        payload: u.data,
      });
      let next = q.get("next");
      nav(next ? next : "/");
    } catch (ex) {
      console.error(ex);
      setMsg("Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin!");
    } finally {
      setLoading(false);
    }
  };
  
  return (
    <>
      <div className="bg-light min-vh-100 py-5">
        <Container>
          <Row
            className="justify-content-center align-items-center"
            style={{ minHeight: "80vh" }}
          >
            <Col md={6} lg={4}>
              <Card className="shadow-lg border-0">
                <Card.Header className="bg-dark text-white text-center py-4">
                  <div className="d-flex align-items-center justify-content-center mb-2">
                    <Car size={32} className="me-2" />
                    <h3 className="mb-0 fw-bold">ParkEasy</h3>
                  </div>
                  <p className="mb-0 opacity-75">
                    Đăng nhập vào tài khoản của bạn
                  </p>
                </Card.Header>

                <Card.Body className="p-4">
                  {msg && (
                    <Alert variant="danger" className="mb-4">
                      {msg}
                    </Alert>
                  )}

                  <Form onSubmit={login}>
                    <Form.Group className="mb-3" controlId="username">
                      <Form.Label className="fw-medium d-flex align-items-center">
                        <User size={16} />
                        <span className="ms-2">Tên đăng nhập</span>
                      </Form.Label>
                      <Form.Control
                        required
                        value={user.username || ""}
                        onChange={(e) =>
                          setUser({ ...user, username: e.target.value })
                        }
                        type="text"
                        placeholder="Nhập tên đăng nhập"
                        className="py-3"
                      />
                    </Form.Group>

                    <Form.Group className="mb-4" controlId="password">
                      <Form.Label className="fw-medium d-flex align-items-center">
                        <Lock size={16} />
                        <span className="ms-2">Mật khẩu</span>
                      </Form.Label>
                      <Form.Control
                        required
                        value={user.password || ""}
                        onChange={(e) =>
                          setUser({ ...user, password: e.target.value })
                        }
                        type="password"
                        placeholder="Nhập mật khẩu"
                        className="py-3"
                      />
                    </Form.Group>

                    <div className="d-flex justify-content-between align-items-center mb-4">
                      <Form.Check
                        type="checkbox"
                        id="remember"
                        label="Ghi nhớ đăng nhập"
                        className="text-sm"
                      />
                      <Link
                        to="/forgot-password"
                        className="text-decoration-none text-sm"
                      >
                        Quên mật khẩu?
                      </Link>
                    </div>

                    <div className="d-grid gap-2">
                      {loading ? (
                        <Button
                          variant="success"
                          disabled
                          size="lg"
                          className="py-3"
                        >
                          <span
                            className="spinner-border spinner-border-sm me-2"
                            role="status"
                            aria-hidden="true"
                          ></span>
                          Đang đăng nhập...
                        </Button>
                      ) : (
                        <Button
                          type="submit"
                          variant="dark"
                          size="lg"
                          className="py-3"
                        >
                          <LogIn size={20} className="me-2" />
                          Đăng nhập
                        </Button>
                      )}
                    </div>
                  </Form>

                  <hr className="my-4" />

                  <div className="text-center">
                    <span className="text-muted">Chưa có tài khoản? </span>
                    <Link
                      to="/register"
                      className="text-decoration-none fw-medium"
                    >
                      Đăng ký ngay
                    </Link>
                  </div>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </Container>
      </div>
    </>
  );
};
export default Login;
