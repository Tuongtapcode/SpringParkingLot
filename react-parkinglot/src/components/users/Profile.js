import {
  Lock,
  Mail,
  Phone,
  User,
  User2,
  UserCheck,
  Save,
  Camera,
} from "lucide-react";
import { useRef, useState, useEffect, useContext } from "react";
import {
  Alert,
  Button,
  Card,
  Col,
  Container,
  Form,
  Row,
  Image,
} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { MyUserContext } from "../../configs/Contexts";
import { authApis, endpoints } from "../../configs/Apis";

const Profile = () => {
  const info = [
    {
      title: "Tên",
      field: "firstName",
      type: "text",
      icon: <User size={16} />,
    },
    {
      title: "Họ và tên lót",
      field: "lastName",
      type: "text",
      icon: <User2 size={16} />,
    },
    {
      title: "Số điện thoại",
      field: "phone",
      type: "tel",
      icon: <Phone size={16} />,
    },
    {
      title: "Email",
      field: "email",
      type: "email",
      icon: <Mail size={16} />,
    },
  ];

  const avatar = useRef();
  const [user, setUser] = useState({});
  const [currentUser, dispatch] = useContext(MyUserContext);
  const [msg, setMsg] = useState();
  const [msgType, setMsgType] = useState("danger");
  const [loading, setLoading] = useState(false);
  const [previewAvatar, setPreviewAvatar] = useState(null);
  const nav = useNavigate();

  // Load current user data
  useEffect(() => {
    if (currentUser) {
      setUser({
        firstName: currentUser.firstName || "",
        lastName: currentUser.lastName || "",
        phone: currentUser.phone || "",
        email: currentUser.email || "",
        password: "",
        confirm: "",
      });
      setPreviewAvatar(currentUser.avatar);
    } else {
      nav("/login?next=/profile");
    }
  }, [currentUser, nav]);

  const handleAvatarChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setPreviewAvatar(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  const updateProfile = async (event) => {
    event.preventDefault();
    try {
      setLoading(true);
      let formData = new FormData();
      
      for (let key in user) {
        if (key !== "confirm" && user[key] !== "") {
          if (key === "password" && user[key] === "") {
            continue;
          }
          formData.append(key, user[key]);
        }
      }

      if (avatar.current.files.length > 0) {
        formData.append("avatar", avatar.current.files[0]);
      }

      let res = await authApis().put(endpoints["profile"], formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      if (res.status === 200) {
        setMsg("Cập nhật thông tin thành công!");
        setMsgType("success");

        dispatch({
          type: "login",
          payload: res.data,
        });
      }
    } catch (ex) {
      console.error(ex);
      setMsg("Cập nhật thông tin thất bại. Vui lòng thử lại!");
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
                    <UserCheck size={32} className="me-2" />
                    <h3 className="mb-0 fw-bold">THÔNG TIN CÁ NHÂN</h3>
                  </div>
                  <p className="mb-0 opacity-75">
                    Cập nhật thông tin tài khoản của bạn
                  </p>
                </Card.Header>

                <Card.Body className="p-4">
                  {msg && (
                    <Alert variant={msgType} className="mb-4">
                      {msg}
                    </Alert>
                  )}

                  <Form onSubmit={updateProfile}>
                    {/* Avatar Section */}
                    <div className="text-center mb-4">
                      <div className="position-relative d-inline-block">
                        <Image
                          src={
                            previewAvatar 
                          }
                          roundedCircle
                          width={120}
                          height={120}
                          className="border border-3 border-success"
                          style={{ objectFit: "cover" }}
                        />
                        <Button
                          variant="success"
                          size="sm"
                          className="position-absolute bottom-0 end-0 rounded-circle p-2"
                          onClick={() => avatar.current?.click()}
                          type="button"
                        >
                          <Camera size={16} />
                        </Button>
                      </div>
                      <Form.Control
                        type="file"
                        ref={avatar}
                        accept="image/*"
                        className="d-none"
                        onChange={handleAvatarChange}
                      />
                      <div className="mt-2 text-muted small">
                        Nhấp vào biểu tượng camera để thay đổi ảnh đại diện
                      </div>
                    </div>

                    <Row>
                      {info.map((i, index) => (
                        <Col
                          md= {12}
                          key={i.field}
                          className="mb-3"
                        >
                          <Form.Group controlId={i.field}>
                            <Form.Label className="fw-medium d-flex align-items-center">
                              {i.icon}
                              <span className="ms-2">{i.title}</span>
                            </Form.Label>
                            <Form.Control
                              required={i.field}
                              value={user[i.field]}
                              onChange={(e) =>
                                setUser({ ...user, [i.field]: e.target.value })
                              }
                              type={i.type}
                              className="py-2"
                            />
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
                          Lưu thay đổi
                        </Button>
                      )}
                    </div>
                  </Form>

                  <hr className="my-4" />

                  <div className="text-center">
                    <span className="text-muted">Tên đăng nhập: </span>
                    <span className="fw-medium">{currentUser.username}</span>
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

export default Profile;
