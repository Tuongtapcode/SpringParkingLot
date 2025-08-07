import {
  Settings as SettingsIcon,
  Bell,
  Moon,
  Sun,
  Globe,
  Smartphone,
  Car,
  HelpCircle,
  Save,
  User,
  Lock,
} from "lucide-react";
import {
  Button,
  Card,
  Col,
  Container,
  Form,
  Row,
  ListGroup,
} from "react-bootstrap";
import { Link } from "react-router-dom";
import AlertNoUser from "../layout/AlertNoUser";
import { MyUserContext } from "../../configs/Contexts";
import { useContext } from "react";

const Settings = () => {
  const [user] = useContext(MyUserContext);
  if (user === null) {
    return (
      <>
        <AlertNoUser title="settings" urlNext="/login?next=/settings" />
      </>
    );
  }
  return (
    <>
      <div className="bg-light min-vh-100 py-5">
        <Container>
          <Row className="justify-content-center">
            <Col lg={8}>
              {/* Header */}
              <Card className="shadow-lg border-0 mb-4">
                <Card.Header className="bg-info text-white text-center py-4">
                  <div className="d-flex align-items-center justify-content-center mb-2">
                    <SettingsIcon size={32} className="me-2" />
                    <h3 className="mb-0 fw-bold">CÀI ĐẶT HỆ THỐNG</h3>
                  </div>
                  <p className="mb-0 opacity-75">
                    Tùy chỉnh trải nghiệm ParkEasy của bạn
                  </p>
                </Card.Header>
              </Card>

              {/* Quick Actions */}
              <Card className="shadow border-0 mb-4">
                <Card.Header className="bg-white border-bottom">
                  <h5 className="mb-0">Thao tác nhanh</h5>
                </Card.Header>
                <Card.Body>
                  <ListGroup variant="flush">
                    <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                      <div className="d-flex align-items-center">
                        <User size={20} className="me-3 text-primary" />
                        <div>
                          <div className="fw-medium">Thông tin cá nhân</div>
                          <small className="text-muted">
                            Cập nhật thông tin tài khoản
                          </small>
                        </div>
                      </div>
                      <Link
                        to="/profile"
                        className="btn btn-outline-primary btn-sm"
                      >
                        Chỉnh sửa
                      </Link>
                    </ListGroup.Item>

                    <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                      <div className="d-flex align-items-center">
                        <Lock size={20} className="me-3 text-warning" />
                        <div>
                          <div className="fw-medium">Bảo mật</div>
                          <small className="text-muted">
                            Đổi mật khẩu, xác thực 2 lớp
                          </small>
                        </div>
                      </div>
                      <Link
                        className="btn btn-warning"
                        to={"/settings/changepassword"}
                        size="sm"
                      >
                        Cài đặt
                      </Link>
                    </ListGroup.Item>

                    <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                      <div className="d-flex align-items-center">
                        <HelpCircle size={20} className="me-3 text-info" />
                        <div>
                          <div className="fw-medium">Trợ giúp & Hỗ trợ</div>
                          <small className="text-muted">
                            FAQ, liên hệ hỗ trợ
                          </small>
                        </div>
                      </div>
                      <Button className="btn btn-primary" size="sm">
                        Liên hệ
                      </Button>
                    </ListGroup.Item>
                  </ListGroup>
                </Card.Body>
              </Card>
              {/* Notification Settings */}
              <Card className="shadow border-0 mb-4">
                <Card.Header className="bg-white border-bottom">
                  <h5 className="mb-0 d-flex align-items-center">
                    <Bell size={20} className="me-2 text-primary" />
                    Cài đặt thông báo
                  </h5>
                </Card.Header>
                <Card.Body>
                  <Row>
                    <Col md={6}>
                      <Form.Check
                        type="switch"
                        id="emailNotifications"
                        label="Thông báo qua Email"
                        checked={true}
                        className="mb-3"
                      />
                    </Col>
                  </Row>
                </Card.Body>
              </Card>

              {/* App Settings */}
              <Card className="shadow border-0 mb-4">
                <Card.Header className="bg-white border-bottom">
                  <h5 className="mb-0 d-flex align-items-center">
                    <Smartphone size={20} className="me-2 text-success" />
                    Cài đặt ứng dụng
                  </h5>
                </Card.Header>
                <Card.Body>
                  <Row>
                    <Col md={6}>
                      <Form.Group className="mb-3">
                        <Form.Label className="d-flex align-items-center">
                          {true ? <Moon size={16} /> : <Sun size={16} />}
                          <span className="ms-2">Chế độ giao diện</span>
                        </Form.Label>
                        <Form.Check
                          type="switch"
                          id="darkMode"
                          label="Chế độ tối"
                          checked={true}
                        />
                      </Form.Group>

                      <Form.Group className="mb-3">
                        <Form.Label className="d-flex align-items-center">
                          <Globe size={16} />
                          <span className="ms-2">Ngôn ngữ</span>
                        </Form.Label>
                        <Form.Select value={"Tiếng anh"}>
                          <option>Tiếng việt</option>
                        </Form.Select>
                      </Form.Group>
                    </Col>
                  </Row>
                </Card.Body>
              </Card>

              {/* Parking Preferences */}
              <Card className="shadow border-0 mb-4">
                <Card.Header className="bg-white border-bottom">
                  <h5 className="mb-0 d-flex align-items-center">
                    <Car size={20} className="me-2 text-warning" />
                    Tùy chọn đậu xe
                  </h5>
                </Card.Header>
                <Card.Body>
                  <Row>
                    <Col md={6}>
                      <Form.Group className="mb-3">
                        <Form.Label>Phương thức thanh toán ưa thích</Form.Label>
                        <Form.Select>
                          <option>Thẻ tín dụng</option>
                          <option>Môm</option>
                        </Form.Select>
                      </Form.Group>
                    </Col>
                  </Row>
                </Card.Body>
              </Card>

              {/* Action Buttons */}
              <div className="d-flex gap-3 mb-4">
                <Button variant="success" size="lg" className="flex-fill">
                  <>
                    <Save size={20} className="me-2" />
                    Lưu cài đặt
                  </>
                </Button>
              </div>

              {/* App Info */}
              <Card className="shadow border-0">
                <Card.Body className="text-center">
                  <div className="text-muted small">
                    <div>ParkEasy v1.0.0</div>
                    <div>© 2025 ParkEasy. All rights reserved.</div>
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

export default Settings;
