import { useContext, useEffect, useState } from "react";
import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import Apis from "../../configs/Apis";
import { Car, UserCircle, UserPlus } from "lucide-react";
import { Link } from "react-router-dom";
import { MyUserContext } from "../../configs/Contexts";

const Header = () => {
  const [parkinglots, setParkingLots] = useState([]);
  const [user, dispatch] = useContext(MyUserContext);
  const loadParkingLots = async () => {
    let response = await Apis.get("/parkinglots");
    setParkingLots(response.data);
  };

  useEffect(() => {
    loadParkingLots();
  }, []);
  return (
    <>
      <Navbar bg="dark" variant="dark" expand="lg" className="shadow-sm">
        <Container>
          <Navbar.Brand className="fw-bold d-flex align-items-center">
            <Car className="me-2" size={28} />
            ParkEasy
          </Navbar.Brand>

          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link as={Link} to="/" className="fw-medium">
                Trang chủ
              </Nav.Link>

              <NavDropdown title="Chỗ đỗ xe" id="basic-nav-dropdown">
                <NavDropdown.Item
                  as={Link}
                  to={`/spaces`}
                  key={0}
                  className="fw-medium"
                >
                  Tất cả bãi đỗ
                </NavDropdown.Item>
                {parkinglots.map((parkinglot) => (
                  <NavDropdown.Item
                    as={Link}
                    to={`/spaces?pklId=${parkinglot.id}`}
                    key={parkinglot.id}
                    className="fw-medium"
                  >
                    {parkinglot.name}
                  </NavDropdown.Item>
                ))}
              </NavDropdown>

              <Nav.Link as={Link} to={"/bookings"} className="fw-medium">
                Đặt chỗ của tôi
              </Nav.Link>
              <Nav.Link as={Link} to={"/payments"} className="fw-medium">
                Thanh toán
              </Nav.Link>
              <Nav.Link className="fw-medium">Đánh giá</Nav.Link>
            </Nav>

            <Nav className="align-items-center">
              {user === null ? (
                <>
                  <Nav.Link
                    as={Link}
                    to="/login"
                    className="fw-medium text-primary me-2"
                  >
                    <UserCircle size={16} className="me-1" />
                    Đăng nhập
                  </Nav.Link>
                  <Nav.Link
                    as={Link}
                    to="/register"
                    className="fw-medium text-success me-2"
                  >
                    <UserPlus size={16} className="me-1" />
                    Đăng ký
                  </Nav.Link>
                </>
              ) : (
                <div className="d-flex align-items-center">
                  <img
                    src={user.avatar || "/default-avatar.png"}
                    alt="Avatar"
                    width={32}
                    height={32}
                    className="rounded-circle me-2"
                    style={{ objectFit: "cover" }}
                  />
                  <NavDropdown
                    title={`Chào ${user.username}`}
                    className="fw-medium"
                    align="end"
                  >
                    <NavDropdown.Item as={Link} to="/profile">
                      <i className="bi bi-person me-2"></i>
                      Hồ sơ
                    </NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/bookings">
                      <i className="bi bi-calendar-check me-2"></i>
                      Đặt chỗ của tôi
                    </NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/settings">
                      <i className="bi bi-gear me-2"></i>
                      Cài đặt
                    </NavDropdown.Item>
                    <NavDropdown.Divider />
                    <NavDropdown.Item
                      onClick={(e) => {
                        e.preventDefault();
                        dispatch({ type: "logout" });
                      }}
                      className="text-danger"
                    >
                      <i className="bi bi-box-arrow-right me-2"></i>
                      Đăng xuất
                    </NavDropdown.Item>
                  </NavDropdown>
                </div>
              )}
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </>
  );
};

export default Header;
