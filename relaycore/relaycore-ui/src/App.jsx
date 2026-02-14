import WebhookRelay from './components/WebhookRelay';
import './App.css'; // This is now empty, which is what we want

function App() {
  return (
    <div style={appStyles.wrapper}>
      <header style={appStyles.header}>
        <h1 style={appStyles.title}>RelayCore Dashboard</h1>
      </header>
      <main style={appStyles.main}>
        <WebhookRelay />
      </main>
    </div>
  );
}

const appStyles = {
  wrapper: {
    display: 'flex',
    flexDirection: 'column',
    minHeight: '100vh',
    backgroundColor: '#0f172a',
    margin: 0,
    padding: 0,
  },
  header: {
    padding: '40px 20px 0 20px',
    textAlign: 'center',
  },
  title: {
    color: 'white',
    fontSize: '2.5rem',
    fontWeight: 'bold',
    margin: 0,
    fontFamily: 'sans-serif'
  },
  main: {
    flex: 1,
    display: 'flex',
    alignItems: 'center',    // Centers the card vertically
    justifyContent: 'center', // Centers the card horizontally
    padding: '20px'
  }
};

export default App;