export const getHost = () => {
    const host = location.href;
    if (host.includes(`4200`)) {
        return host.replace('4200', '8080');
    }
    return `${host}`;
}

export const getWebSocketHost = () => {

    const proto = location.protocol == 'http:' ? 'ws://' : 'wss://';
    const host = location.host.replace('4200', '8080');



    return `${proto}/${host}`;
}