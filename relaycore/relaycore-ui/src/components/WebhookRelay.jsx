import React, { useState } from 'react';
import RelayEngineApi from '../api/generated/src/api/RelayEngineApi';

const api = new RelayEngineApi();

const WebhookRelay = () => {
    const [formData, setFormData] = useState({ url: '', data: '' });
    const [loading, setLoading] = useState(false);
    const [status, setStatus] = useState({ message: '', type: '' });

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        setStatus({ message: 'Sending to RelayCore...', type: 'info' });

        api.handle(formData, (error, data, response) => {
            setLoading(false);
            if (error) {
                console.error("Relay failed:", error);
                setStatus({
                    message: `Delivery Failed: ${error.message || 'Check Backend/CORS'}`,
                    type: 'error'
                });
            } else {
                setStatus({ message: 'Success! Message queued in Kafka.', type: 'success' });
                setFormData({ url: '', data: '' });
            }
        });
    };

    return (
        <div style={styles.container}>
            <h2 style={styles.cardTitle}>RelayCore Control Plane</h2>

            <form onSubmit={handleSubmit}>
                <div style={styles.fieldGroup}>
                    <label style={styles.label}>Destination URL</label>
                    <input
                        style={styles.input}
                        placeholder="https://webhook.site/your-uuid"
                        value={formData.url}
                        onChange={(e) => setFormData({ ...formData, url: e.target.value })}
                        required
                    />
                </div>

                <div style={styles.fieldGroup}>
                    <label style={styles.label}>JSON Payload</label>
                    <textarea
                        style={styles.textarea}
                        placeholder='{"event": "user_signup", "id": 101}'
                        value={formData.data}
                        onChange={(e) => setFormData({ ...formData, data: e.target.value })}
                        required
                    />
                </div>

                <button type="submit" style={styles.button} disabled={loading}>
                    {loading ? "Processing..." : "Dispatch Webhook"}
                </button>
            </form>

            {status.message && (
                <div style={{
                    ...styles.status,
                    backgroundColor: status.type === 'error' ? '#ef4444' : status.type === 'success' ? '#22c55e' : '#3b82f6'
                }}>
                    {status.message}
                </div>
            )}
        </div>
    );
};

const styles = {
    container: {
        padding: '30px',
        backgroundColor: '#1e293b',
        borderRadius: '16px',
        color: 'white',
        width: '100%',
        maxWidth: '480px',
        fontFamily: 'sans-serif',
        boxShadow: '0 25px 50px -12px rgba(0, 0, 0, 0.5)',
        border: '1px solid #334155'
    },
    cardTitle: { fontSize: '1.5rem', marginBottom: '24px', textAlign: 'center', color: '#38bdf8' },
    fieldGroup: { marginBottom: '20px' },
    label: { display: 'block', marginBottom: '8px', fontSize: '0.875rem', color: '#94a3b8' },
    input: {
        width: '100%', padding: '12px', borderRadius: '8px', border: '1px solid #334155',
        backgroundColor: '#0f172a', color: 'white', boxSizing: 'border-box', outline: 'none'
    },
    textarea: {
        width: '100%', padding: '12px', height: '140px', borderRadius: '8px', border: '1px solid #334155',
        backgroundColor: '#0f172a', color: 'white', boxSizing: 'border-box', fontFamily: 'monospace', outline: 'none'
    },
    button: {
        width: '100%', padding: '14px', backgroundColor: '#0284c7', color: 'white', border: 'none',
        borderRadius: '8px', cursor: 'pointer', fontWeight: 'bold', fontSize: '1rem', transition: '0.2s'
    },
    status: {
        marginTop: '24px', padding: '12px', borderRadius: '8px', textAlign: 'center', fontSize: '0.9rem', fontWeight: 'bold'
    }
};

export default WebhookRelay;