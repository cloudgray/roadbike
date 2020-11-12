
import './App.css';
import axios from 'axios';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import crankset from './images/crankset.png';
import allez2021 from './images/allez2021.jpg';
import bikeshop from './images/bikeshop.jpg'
import { roadbikeApi } from './api.js';

function App() {
  return (
    <div className="App" style={{height:'100vh', textAlign: 'center', backgroundColor: 'black'}}>
      <script crossorigin src="anonymous"></script>
      <link
        rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
        integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
        crossorigin="anonymous"
      />
      <Container style={{width: '100vw', margin: '0 0 0 0', marginRight: '0'}}>
        <Row>
          <Col style={{justifyContent: 'center'}}>
            <Card style={{ width: '27vw' }}>
              <Card.Img variant="top" src={crankset} />
              <Card.Body>
                <Card.Title>Shimano</Card.Title>
                <Card.Text>
                Throughout its over 90-year history,
Shimano has constantly been at the forefront, developing cutting-edge technologies and products.
Shimano develops products that meet the global demand for superior quality bicycle components, fishing tackle and rowing equipment through original technologies and innovative ideas.
                </Card.Text>
                <Button variant="primary" onClick={roadbikeApi.produceRoadbike}>Produce Crankset</Button>
                <p><a href="http://localhost:8080/shimano/crankset">자전거 부품제조사 Shimno - 변속기 "Claris" 생산</a></p>
                <div>재고 : </div>
              </Card.Body>
            </Card>
          </Col>
          <Col>
            <Card style={{ width: '27vw' }}>
              <Card.Img variant="top" src={allez2021} />
              <Card.Body>
                <Card.Title>Specialized</Card.Title>
                <Card.Text>
                  
If there’s one thing that unites the people who work here, it’s this: We believe that bicycles have the power to change lives. That’s why we’re always looking for passionate people from all disciplines who share in this belief and who are looking to play an active role in inspiring and getting more people on bikes. Are you ready to make a difference?
                </Card.Text>
                <Button variant="primary" onClick={produceRoadbike}>Produce Roadbike</Button>
              </Card.Body>
            </Card>
          </Col>
          <Col>
            <Card style={{ width: '27vw' }}>
              <Card.Img variant="top" src={bikeshop} />
              <Card.Body>
                <Card.Title>RealBikeShop</Card.Title>
                <Card.Text>
                  Local bike shop close to home Local bike shop close to home Local bike shop close to home Local bike shop close to home Local bike shop close to home Local bike shop close to home Local bike shop close to home Local bike shop close to home Local bike shop close to home Local bike shop close to home Local bike shop close to home Local bike shop close to home
                </Card.Text>
                <Button variant="primary">Warehouse Roadbike</Button>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

const produceRoadbike = () => {
  axios.get("http://localhost:8080/shimano/crankset", {
    headers: {
      'Access-Control-Allow-Origin': "http://localhost:8080/shimano/crankset", 
      'Access-Control-Allow-Credentials': false
    }
  }).then(res => {
    console.log(res)
  })
}

// const produceRoadbike2 = () => {
//   axios.get('http://localhost:8080/shimano/crankset',(req, res) => {
//     console.log(res);
//   })
// }


export default App;
