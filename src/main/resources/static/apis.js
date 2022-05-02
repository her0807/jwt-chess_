const API_HOST = "http://localhost:8080"

const fetchAsGet = async (path) => {
    const response = await axios.get(`${API_HOST}${path}`);
    return response.data;
}

const fetchAsPost = async (path, body) => {
    const response = await axios.post(`${API_HOST}${path}`, body);
    return response.data;
}

const fetchAsDelete = async (path) => {
    const response = await axios.delete(`${API_HOST}${path}`);
    return response;
}

const fetchRooms = async () => {
    return fetchAsGet("/rooms");
}

const fetchBoard = async (roomId) => {
    return fetchAsGet(`/rooms/${roomId}/board`);
}

const fetchCurrentTurn = async (roomId) => {
    return fetchAsGet(`/rooms/${roomId}/turn`);
}

const fetchScore = async (roomId) => {
    return fetchAsGet(`/rooms/${roomId}/score`);
}

const fetchWinner = async (roomId) => {
    return fetchAsGet(`/rooms/${roomId}/winner`);
}

const createRoom = async (title, password) => {
    return await fetchAsPost(`/rooms`, {title, password});
}

const deleteRoom = async (id, password) => {
    return await fetchAsDelete(`/rooms/${id}?password=${password}`);
}

const move = async (roomId, from, to) => {
    return await fetchAsPost(`/rooms/${roomId}/move`, {from, to})
}

