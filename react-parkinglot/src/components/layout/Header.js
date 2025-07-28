import { useEffect, useState } from "react";
import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import Apis from "../../configs/Apis";

const Header = () => {
  const [categories, setCategories] = useState([]);

  const loadCategories = async () => {
    let response = await Apis.get("/categories");
    setCategories(response.data);
  };

  useEffect(() => {
    loadCategories();
  }, []);
  return (
    <>
      <Navbar expand="lg" className="bg-body-tertiary">
        <Container>
          <Navbar.Brand href="#home">React-Bootstrap</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link href="#home">Home</Nav.Link>
              <Nav.Link href="#link">Link</Nav.Link>
              <NavDropdown title="Danh mục" id="basic-nav-dropdown">
                {categories.map((category) => (
                  <NavDropdown.Item key={category.id} href={`#category/${category.id}`}>
                    {category.name}
                  </NavDropdown.Item>
                ))}
              </NavDropdown>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </>
  );
};

export default Header;