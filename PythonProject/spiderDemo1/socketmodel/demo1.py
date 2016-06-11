import socket
from binascii import hexlify


def print_machine_info():
    host_name = socket.gethostname()
    ip_address = socket.gethostbyname(host_name)
    print("Host name is %s" % host_name)
    print("IP address is %s" % ip_address)


def convert_ip4_address():
    for ip_addr in ['127.0.0.1','192.168.0.1']:
        packed_ip_address = socket.inet_aton(ip_addr)
        unpacked_ip_address = socket.inet_ntoa(packed_ip_address)
        print ("IP Address: %s => Packed:%s,Unpacked: %s" \
                       %(ip_addr,hexlify(packed_ip_address),unpacked_ip_address) )


def find_service_name():
    protocolname = 'tcp'
    for port in [80, 25]:
        print("Port : %s => service name : %s" %(port,socket.getservbyport(port,protocolname)))
    print("Port :%s => service name : %s" %(53,'udp'))

if __name__ == '__main__':
    find_service_name()
