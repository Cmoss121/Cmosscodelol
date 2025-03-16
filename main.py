import time
import sys
import string
import itertools
import threading
import hashlib
import base64
import socket
import requests
import os
import random
# import pynput
import pyfiglet
from cryptography.fernet import Fernet
from scapy.layers.inet import IP, ICMP, UDP
from scapy.layers.l2 import ARP, Ether
from scapy.sendrecv import srp, send
from stegano import lsb
from faker import Faker
from colorama import Fore, Style, init

init(autoreset=True)

def typing_print(text, delay=0.02):
    for char in text:
        sys.stdout.write(Fore.GREEN + Style.BRIGHT + char + Style.RESET_ALL)
        sys.stdout.flush()
        time.sleep(delay)

def loading_animation(text, duration=2):
    symbols = ['⣾', '⣽', '⣻', '⢿', '⡿', '⣟', '⣯', '⣷']
    end_time = time.time() + duration
    i = 0
    while time.time() < end_time:
        sys.stdout.write(f'\r{Fore.GREEN}{text} {symbols[i]} ' + Style.RESET_ALL)
        sys.stdout.flush()
        time.sleep(0.1)
        i = (i + 1) % len(symbols)
    sys.stdout.write('\r' + ' ' * (len(text) + 4) + '\r')
    sys.stdout.flush()

ascii_art = """
_____________________________________________

 ██████╗███╗   ███╗ ██████╗ ███████╗███████╗
██╔════╝████╗ ████║██╔═══██╗██╔════╝██╔════╝
██║     ██╔████╔██║██║   ██║███████╗███████╗
██║     ██║╚██╔╝██║██║   ██║╚════██║╚════██║
╚██████╗██║ ╚═╝ ██║╚██████╔╝███████║███████║
 ╚═════╝╚═╝     ╚═╝ ╚═════╝ ╚══════╝╚══════╝
_____________________________________________ 
GO DM ME ON cmoss8227@gmail.com

"""

os.system('clear' if os.name == 'posix' else 'cls')
typing_print(ascii_art)
loading_animation("INITIALIZING SYSTEM")
loading_animation("LOADING SECURITY MODULES")
loading_animation("ESTABLISHING SECURE CONNECTION")
print(Fore.GREEN + "\n[ACCESS GRANTED]" + Style.RESET_ALL)
print("=" * 45 + "\n")
sys.stdout.flush()
time.sleep(0.002) 
# Flashing Welcome Text
text = "[INITIALIZING SECURITY INTERFACE...]"
for c in text:
    sys.stdout.write(Fore.GREEN + Style.BRIGHT + c + Style.RESET_ALL)
    sys.stdout.flush()
    time.sleep(0.05)
print("\n" + "="*45)

print("\n")

# Main menu function
def fake_networks_rickroll():
    networks = [
        {"ssid": "FBI Surveillance Van", "security": "WPA2", "signal": -45, "channel": 1},
        {"ssid": "Not A Virus", "security": "Open", "signal": -60, "channel": 6},
        {"ssid": "Click Here For Free WiFi", "security": "Open", "signal": -55, "channel": 11},
        {"ssid": "Loading Rickroll...", "security": "WPA2", "signal": -50, "channel": 3},
        {"ssid": "Protected Network 2.4GHz", "security": "WPA3", "signal": -65, "channel": 8}
    ]

    os.system('clear' if os.name == 'posix' else 'cls')
    print(Fore.MAGENTA + "╔══════════════════════════════════════╗")
    print(Fore.MAGENTA + "║        Available WiFi Networks        ║")
    print(Fore.MAGENTA + "╚══════════════════════════════════════╝\n")

    for i, network in enumerate(networks, 1):
        signal_bars = "█" * (4 - abs(network["signal"]) // 15)
        print(Fore.MAGENTA + f"{i}. {network['ssid']}")
        print(Fore.MAGENTA + f"   Signal: {signal_bars} ({network['signal']} dBm)")
        print(Fore.MAGENTA + f"   Security: {network['security']}")
        print(Fore.MAGENTA + f"   Channel: {network['channel']}\n")

    choice = input(Fore.MAGENTA + "Enter network number to connect (or 'q' to quit): " + Style.RESET_ALL)
    
    try:
        if choice.lower() == 'q':
            return
        idx = int(choice) - 1
        if 0 <= idx < len(networks):
            print(Fore.MAGENTA + "\nConnecting to " + networks[idx]["ssid"] + "...")
            loading = ["⠋","⠙","⠹","⠸","⠼","⠴","⠦","⠧","⠇","⠏"]
            for _ in range(20):
                for char in loading:
                    sys.stdout.write(Fore.MAGENTA + f"\rAttempting connection {char}")
                    sys.stdout.flush()
                    time.sleep(0.1)
            print(Fore.MAGENTA + "\nGet Rick Rolled! :)")
            time.sleep(2)
            os.system('python -m webbrowser -t "https://www.youtube.com/watch?v=dQw4w9WgxcQ"')
        else:
            print(Fore.MAGENTA + "\nInvalid network number!")
            time.sleep(2)
    except ValueError:
        print(Fore.MAGENTA + "\nPlease enter a valid number!")
        time.sleep(2)

def main_menu():
    while True:
        tools = """
╭───────────────────────────────────────────╮
│          CMOSS SECURITY TOOLKIT           │
├───────────────────────────────────────────┤
│  SYSTEM & NETWORK                         │
│  1.  ⌨️  Keylogger                        │
│  2.  📡 Network Scanner                   │
│  3.  📦 Packet Sender                     │
│  4.  🌐 Port Scanner                      │
│  5.  📍 IP Geolocation                    │
│  6.  🔍 DNS Lookup                        │
│  7.  🌐 Subnet Calculator                 │
│  8.  📡 Ping Sweep                        │
│                                           │
│  SECURITY TESTING                         │
│  9.  🔒 Hash Cracker                      │
│  10. 🗃️ SQL Injection Tester             │
│  11. 🔑 Password Cracker                  │
│  12. 📂 Directory Traversal              │
│  13. 🔐 SSL Certificate Checker          │
│  14. 📋 HTTP Header Analyzer             │
│                                           │
│  ENCRYPTION & DATA                        │
│  15. 🔒 File Encryptor/Decryptor         │
│  16. 📝 File Integrity Checker           │
│  17. 🎭 Steganography                    │
│  18. 🔑 Password Generator               │
│                                           │
│  SIMULATION & DEMOS                       │
│  19. 🤖 Botnet Simulator                 │
│  20. 🌊 DDoS Simulation                  │
│  21. 📡 Fake Networks                    │
│                                           │
│  UTILITIES                               │
│  22. 👤 Fake Identity Generator          │
│  23. 📊 System Resource Monitor          │
│  24. 🔍 Network Traffic Analyzer         │
│  25. 📺 Matrix Rain Effect              │
│  26. 🎮 Retro Game Console              │
│  27. 📟 ASCII Art Generator             │
│  28. Exit                                │
╰───────────────────────────────────────────╯"""

        loading_animation("GENERATING INTERFACE")
        for line in tools.split('\n'):
            time.sleep(0.02)
            print(Fore.GREEN + Style.BRIGHT + line + Style.RESET_ALL)
            time.sleep(0.3)

        print(Fore.MAGENTA + "\nEnter tool number: ")

        choice = input().strip()

        try:
            choice = int(choice)
            if choice == 1: keylogger()
            elif choice == 2: steganography()
            elif choice == 3: botnet_simulator()
            elif choice == 4: hash_cracker()
            elif choice == 5: network_scanner()
            elif choice == 6: file_encryptor()
            elif choice == 7: fake_identity()
            elif choice == 8: send_packets()
            elif choice == 9: ip_geolocation()
            elif choice == 10: ddos_simulation()
            elif choice == 11: sql_injection_tester()
            elif choice == 12: password_cracker()
            elif choice == 13: subnet_calculator()
            elif choice == 14: dns_lookup()
            elif choice == 15: file_integrity_checker()
            elif choice == 16: ping_sweep()
            elif choice == 17: password_generator()
            elif choice == 18:
                fake_networks_rickroll()
                continue
            elif choice == 19:
                port_scanner()
            elif choice == 20:
                directory_traversal_tester()
            elif choice == 21:
                ssl_cert_checker()
            elif choice == 22:
                http_header_analyzer()
            elif choice == 23:
                system_resource_monitor()
            elif choice == 24:
                network_traffic_analyzer()
            elif choice == 25:
                matrix_rain()
            elif choice == 26:
                retro_console()
            elif choice == 27:
                text = input(Fore.MAGENTA + "Enter text to convert to ASCII art: ")
                try:
                    ascii_text = pyfiglet.figlet_format(text, font='slant')
                    print(Fore.GREEN + ascii_text)
                except Exception as e:
                    print(Fore.MAGENTA + f"Error generating ASCII art: {str(e)}")
                continue
            elif choice == 28:
                print(Fore.MAGENTA + "\n[SYSTEM SHUTDOWN INITIATED...]\n")
                time.sleep(1)
                print("Goodbye!")
                break
            else:
                print(Fore.MAGENTA + "Invalid choice. Please enter a number between 1 and 19.")
        except ValueError:
            print(Fore.MAGENTA + "Invalid input. Please enter a number.")
        else:
            print(Fore.MAGENTA + "Invalid choice. Please try again.")

        input(Fore.MAGENTA + "\nPress Enter to continue...")

# Keylogger (Logs Keystrokes)
def keylogger():
    print(Fore.MAGENTA + "Simple Keylogger Demo")
    print(Fore.MAGENTA + "Type something (press Enter twice to stop):")
    log_file = "keylog.txt"

    with open(log_file, "w") as f:
        while True:
            user_input = input()
            if user_input == "":
                break
            f.write(user_input + "\n")

    print(Fore.MAGENTA + f"Keylogger stopped. Logs saved to {log_file}")

# Steganography (Hide & Extract Secret Messages in Images)
def steganography():
    choice = input(Fore.MAGENTA + "1 to Hide Message, 2 to Extract: ")
    if choice == "1":
        img_path = input(Fore.MAGENTA + "Enter image path: ")
        secret_msg = input(Fore.MAGENTA + "Enter secret message: ")
        secret_img = "hidden.png"
        lsb.hide(img_path, secret_msg).save(secret_img)
        print(Fore.MAGENTA + "Message hidden in 'hidden.png'")
    elif choice == "2":
        img_path = input(Fore.MAGENTA + "Enter encoded image path: ")
        print(Fore.MAGENTA + "Secret Message: " + lsb.reveal(img_path))

# Botnet Simulator (Educational Purposes Only)
def botnet_simulator():
    print(Fore.MAGENTA + "Simulating a botnet attack...")
    for i in range(10):
        print(Fore.MAGENTA + f"Bot {i+1} sending request to target...")
        time.sleep(0.5)
    print(Fore.MAGENTA + "Simulation Complete.")

# Hash Cracker (MD5, SHA-256)
def hash_cracker():
    hash_type = input(Fore.MAGENTA + "Enter hash type (md5/sha256): ")
    hash_value = input(Fore.MAGENTA + "Enter hash: ")
    wordlist = ["password", "123456", "admin", "letmein", "welcome"]
    for word in wordlist:
        if hash_type == "md5" and hashlib.md5(word.encode()).hexdigest() == hash_value:
            print(Fore.MAGENTA + f"Cracked! Password: {word}")
            return
        if hash_type == "sha256" and hashlib.sha256(word.encode()).hexdigest() == hash_value:
            print(Fore.MAGENTA + f"Cracked! Password: {word}")
            return
    print(Fore.MAGENTA + "Hash not found.")

# Network Scanner (ARP Scan)
def network_scanner():
    target = input(Fore.MAGENTA + "Enter IP address to scan: ")
    try:
        response = os.system(f"ping -c 1 {target} > /dev/null 2>&1")
        if response == 0:
            print(Fore.MAGENTA + f"Host {target} is up!")
        else:
            print(Fore.MAGENTA + f"Host {target} is down!")
    except:
        print(Fore.MAGENTA + "Error scanning network.")

# File Encryptor & Decryptor
def file_encryptor():
    key = Fernet.generate_key()
    cipher = Fernet(key)
    file = input(Fore.MAGENTA + "Enter file to encrypt: ")
    with open(file, "rb") as f:
        encrypted_data = cipher.encrypt(f.read())
    with open(file + ".enc", "wb") as f:
        f.write(encrypted_data)
    print(Fore.MAGENTA + "File encrypted. Key saved in 'key.txt'.")
    with open("key.txt", "wb") as f:
        f.write(key)

def file_decryptor():
    file = input(Fore.MAGENTA + "Enter file to decrypt: ")
    key = open("key.txt", "rb").read()
    cipher = Fernet(key)
    with open(file, "rb") as f:
        decrypted_data = cipher.decrypt(f.read())
    with open(file.replace(".enc", ""), "wb") as f:
        f.write(decrypted_data)
    print(Fore.MAGENTA + "File decrypted.")

# Fake Identity Generator
def fake_identity():
    fake = Faker()
    print(Fore.MAGENTA + f"Fake Name: {fake.name()}")
    print(Fore.MAGENTA + f"Fake Address: {fake.address()}")
    print(Fore.MAGENTA + f"Fake Email: {fake.email()}")

# Packet Sender (Send Raw Packets to IPs)
def send_packets():
    target_ip = input(Fore.MAGENTA + "Enter target IP: ")
    packet_type = input(Fore.MAGENTA + "Enter packet type (tcp/udp): ").lower().strip()
    if packet_type in ['upd', 'udb', 'udp']:
        packet_type = 'udp'
    elif packet_type in ['tcp', 'tpc']:
        packet_type = 'tcp'
    else:
        print(Fore.MAGENTA + "Invalid packet type (use tcp or udp)")
        return

    try:
        packet_count = int(input(Fore.MAGENTA + "Enter number of packets: "))
        if packet_count <= 0:
            print(Fore.MAGENTA + "Packet count must be positive")
            return

        port = int(input(Fore.MAGENTA + "Enter target port: "))
        if port < 1 or port > 65535:
            print(Fore.MAGENTA + "Port must be between 1 and 65535")
            return
    except ValueError:
        print(Fore.MAGENTA + "Please enter valid numbers")
        return

    # Create large packet payload
    payload = b"X" * 1024  # 1KB packet size
    success_count = 0

    try:
        if packet_type == "udp":
            sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
            sock.settimeout(0.1)  # Faster timeout

            for i in range(packet_count):
                try:
                    sock.sendto(payload, (target_ip, port))
                    print(Fore.MAGENTA + f"Packet {i+1}: Sent successfully")
                    success_count += 1
                    time.sleep(0.01)  # Minimal delay between packets
                except:
                    continue

            sock.close()

        elif packet_type == "tcp":
            for i in range(packet_count):
                try:
                    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                    sock.settimeout(1)
                    sock.connect((target_ip, port))
                    sock.send(payload)
                    print(Fore.MAGENTA + f"Packet {i+1}: Sent successfully")
                    success_count += 1
                    sock.close()
                    time.sleep(0.05)
                except:
                    continue
        else:
            print(Fore.MAGENTA + "Invalid packet type (use tcp or udp)")
            return

    except Exception as e:
        print(Fore.MAGENTA + f"Error sending packet: {str(e)}")

    print(Fore.MAGENTA + f"\nPacket sending complete. {success_count} of {packet_count} packets sent successfully.")
    return

# IP Geolocation Lookup
def ip_geolocation():
    ip = input(Fore.MAGENTA + "Enter IP to geolocate: ")
    try:
        response = requests.get(f"http://ip-api.com/json/{ip}?fields=status,message,continent,country,countryCode,region,regionName,city,zip,lat,lon,timezone,isp,org,as,mobile,proxy,hosting").json()

        if response["status"] == "success":
            print(Fore.MAGENTA + "\nLocation Information:")
            print(f"Continent: {response.get('continent', 'N/A')}")
            print(f"Country: {response.get('country', 'N/A')} ({response.get('countryCode', 'N/A')})")
            print(f"Region: {response.get('regionName', 'N/A')} ({response.get('region', 'N/A')})")
            print(f"City: {response.get('city', 'N/A')}")
            print(f"ZIP: {response.get('zip', 'N/A')}")
            print(f"\nCoordinates:")
            print(f"Latitude: {response.get('lat', 'N/A')}")
            print(f"Longitude: {response.get('lon', 'N/A')}")
            print(f"\nNetwork Information:")
            print(f"Timezone: {response.get('timezone', 'N/A')}")
            print(f"ISP: {response.get('isp', 'N/A')}")
            print(f"Organization: {response.get('org', 'N/A')}")
            print(f"AS: {response.get('as', 'N/A')}")
            print(f"\nAdditional Information:")
            print(f"Mobile Network: {response.get('mobile', False)}")
            print(f"Proxy/VPN: {response.get('proxy', False)}")
            print(f"Hosting/Datacenter: {response.get('hosting', False)}")
        else:
            print(Fore.MAGENTA + f"Error: {response.get('message', 'Unknown error')}")
    except Exception as e:
        print(Fore.MAGENTA + f"Error fetching data: {str(e)}")

# DDoS Simulation
def ddos_simulation():
    target_ip = input(Fore.MAGENTA + "Enter IP to target for DDoS simulation: ")
    packet_count = int(input(Fore.MAGENTA + "Enter number of packets to simulate: "))

    for _ in range(packet_count):
        packet = Ether() / IP(dst=target_ip) / ICMP()
        send(packet)
        print(Fore.MAGENTA + f"Sent packet to {target_ip}")
        time.sleep(0.5)

# SQL Injection Tester (Basic)
def sql_injection_tester():
    url = input(Fore.MAGENTA + "Enter the URL to test for SQL Injection: ")
    payload = "' OR 1=1 --"
    try:
        if not url.startswith(('http://', 'https://')):
            url = 'http://' + url
        response = requests.get(url + payload, timeout=5)
        if "SQL syntax" in response.text:
            print(Fore.MAGENTA + "Possible SQL Injection vulnerability found!")
        else:
            print(Fore.MAGENTA + "No vulnerability detected.")
    except requests.exceptions.RequestException as e:
        print(Fore.MAGENTA + f"Error connecting to URL: {str(e)}")

# Password Cracker (Brute Force)
def password_cracker():
    chars = string.ascii_lowercase + string.ascii_uppercase + string.digits + string.punctuation
    target_password = input(Fore.MAGENTA + "Enter the target password: ")
    max_length = 6  # Max password length you want to test

    for length in range(1, max_length + 1):
        for guess in itertools.product(chars, repeat=length):
            guess_password = ''.join(guess)
            print(Fore.MAGENTA + f"Trying: {guess_password}")
            if guess_password == target_password:
                print(Fore.MAGENTA + f"Password found: {guess_password}")
                return
    print(Fore.MAGENTA + "Password not found.")

# Subnet Calculator
def subnet_calculator():
    try:
        ip = input(Fore.MAGENTA + "Enter IP Address (e.g., 192.168.1.1): ")
        subnet_mask = input(Fore.MAGENTA + "Enter Subnet Mask (e.g., 255.255.255.0): ")

        # Convert IP to binary
        ip_parts = ip.split('.')
        if len(ip_parts) != 4:
            raise ValueError("Invalid IP address format")

        ip_binary = ''.join([bin(int(x))[2:].zfill(8) for x in ip_parts])

        # Convert subnet to binary
        mask_parts = subnet_mask.split('.')
        if len(mask_parts) != 4:
            raise ValueError("Invalid subnet mask format")

        mask_binary = ''.join([bin(int(x))[2:].zfill(8) for x in mask_parts])

        # Calculate network address
        network_binary = ''.join('1' if ip_binary[i] == '1' and mask_binary[i] == '1' else '0' for i in range(32))
        network_parts = [str(int(network_binary[i:i+8], 2)) for i in range(0, 32, 8)]
        network_address = '.'.join(network_parts)

        print(Fore.MAGENTA + f"Network Address: {network_address}")
        print(f"Subnet Mask: {subnet_mask}")
        print(f"Number of Hosts: {2 ** (32 - mask_binary.count('1')) - 2}")
    except Exception as e:
        print(Fore.MAGENTA + f"Error: {str(e)}")

# DNS Lookup Tool
def dns_lookup():
    domain = input(Fore.MAGENTA + "Enter domain for DNS lookup: ")
    result = os.popen(f"nslookup {domain}").read()
    print(Fore.MAGENTA + result)

# File Integrity Checker (Hash Comparison)
def file_integrity_checker():
    file_path = input(Fore.MAGENTA + "Enter file path for integrity check: ")
    hash_algo = input(Fore.MAGENTA + "Enter hashing algorithm (md5/sha256): ")
    with open(file_path, "rb") as f:
        file_data = f.read()
        if hash_algo == "md5":
            file_hash = hashlib.md5(file_data).hexdigest()
        elif hash_algo == "sha256":
            file_hash = hashlib.sha256(file_data).hexdigest()
        print(Fore.MAGENTA + f"File hash ({hash_algo}): {file_hash}")

# Ping Sweep Tool
def ping_sweep():
    ip_range = input(Fore.MAGENTA + "Enter IP range (e.g., 192.168.1.1-192.168.1.255): ")
    # Simple ping sweep implementation here
    print(Fore.MAGENTA + f"Pinging IP range: {ip_range}")
    # More advanced implementation can be added for full sweep

# Password Generator
def password_generator():
    length = int(input(Fore.MAGENTA + "Enter password length: "))
    chars = string.ascii_letters + string.digits + string.punctuation
    password = ''.join(random.choice(chars) for _ in range(length))
    print(Fore.MAGENTA + f"Generated Password: {password}")

# Port Scanner
def port_scanner():
    try:
        target = input(Fore.MAGENTA + "Enter target IP: ")
        start_port = int(input(Fore.MAGENTA + "Enter start port: "))
        end_port = int(input(Fore.MAGENTA + "Enter end port: "))

        if not (1 <= start_port <= 65535 and 1 <= end_port <= 65535):
            print(Fore.MAGENTA + "Ports must be between 1-65535")
            return

        for port in range(start_port, end_port + 1):
            sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            sock.settimeout(1)
            result = sock.connect_ex((target, port))
            if result == 0:
                print(Fore.MAGENTA + f"Port {port}: Open")
            sock.close()
    except Exception as e:
        print(Fore.MAGENTA + f"Error: {str(e)}")

# Directory Traversal Tester
def directory_traversal_tester():
    try:
        url = input(Fore.MAGENTA + "Enter target URL: ")
        payloads = ["../", "../../", "../../../", "..\\", "..\\..\\"]

        for payload in payloads:
            test_url = url + payload
            response = requests.get(test_url, timeout=5)
            print(Fore.MAGENTA + f"Testing: {test_url}")
            print(f"Status Code: {response.status_code}")
    except Exception as e:
        print(Fore.MAGENTA + f"Error: {str(e)}")

# SSL Certificate Checker
def ssl_cert_checker():
    try:
        import ssl
        import socket
        hostname = input(Fore.MAGENTA + "Enter hostname: ")
        context = ssl.create_default_context()
        with context.wrap_socket(socket.socket(), server_hostname=hostname) as s:
            s.connect((hostname, 443))
            cert = s.getpeercert()
            print(Fore.MAGENTA + f"Issuer: {dict(x[0] for x in cert['issuer'])}")
            print(f"Expires: {cert['notAfter']}")
    except Exception as e:
        print(Fore.MAGENTA + f"Error: {str(e)}")

# HTTP Header Analyzer
def http_header_analyzer():
    try:
        url = input(Fore.MAGENTA + "Enter URL to analyze: ")
        response = requests.head(url)
        print(Fore.MAGENTA + "\nHTTP Headers:")
        for header, value in response.headers.items():
            print(f"{header}: {value}")
    except Exception as e:
        print(Fore.MAGENTA + f"Error: {str(e)}")

def retro_console():
    print(Fore.MAGENTA + """
╔════════════════════════════╗
║ RETRO CONSOLE v1.0         ║
║ > Type 'help' for commands ║
╚════════════════════════════╝
    """)
    while True:
        cmd = input(Fore.GREEN + "root@retro:~$ " + Style.RESET_ALL).lower()
        if cmd == "exit":
            break
        elif cmd == "help":
            print(Fore.CYAN + """
Available commands:
- help     : Show this help
- whoami   : Display current user
- date     : Show system date
- clear    : Clear screen
- exit     : Exit console
            """)
        elif cmd == "whoami":
            print(Fore.YELLOW + "root")
        elif cmd == "date":
            print(Fore.YELLOW + time.strftime("%Y-%m-%d %H:%M:%S"))
        elif cmd == "clear":
            print("\n" * 50)

def matrix_rain():
    import random
    import time
    chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$%^&*()"
    print(Fore.GREEN + "\nMatrix Rain Effect (Ctrl+C to stop)")
    try:
        while True:
            print(''.join(random.choice(chars) for _ in range(70)))
            time.sleep(0.1)
    except KeyboardInterrupt:
        print(Fore.MAGENTA + "\nMatrix rain stopped.")

# Start the menu
if __name__ == "__main__":
    try:
        main_menu()
    except KeyboardInterrupt:
        print(Fore.MAGENTA + "\nExiting gracefully...")
    except Exception as e:
        print(Fore.MAGENTA + f"\nUnexpected error: {str(e)}")
def system_resource_monitor():
    try:
        print(Fore.MAGENTA + "\nSystem Resource Monitor")
        print("-" * 30)

        # Memory info
        with open('/proc/meminfo') as f:
            total = 0
            available = 0
            for line in f:
                if line.startswith('MemTotal'):
                    total = int(line.split()[1])
                elif line.startswith('MemAvailable'):
                    available = int(line.split()[1])
            if total > 0:
                used_percent = ((total - available) / total) * 100
                print(f"Memory Usage: {used_percent:.1f}%")

        # Disk info
        st = os.statvfs('/')
        free = st.f_bavail * st.f_frsize
        total = st.f_blocks * st.f_frsize
        used_percent = ((total - free) / total) * 100
        print(f"Disk Usage: {used_percent:.1f}%")

        # Network interfaces
        if os.path.exists('/proc/net/dev'):
            print("\nNetwork Interfaces:")
            with open('/proc/net/dev') as f:
                next(f)  # Skip header
                next(f)  # Skip header
                for line in f:
                    interface = line.split(':')[0].strip()
                    if interface != 'lo':  # Skip loopback
                        print(f"  {interface}")
    except Exception as e:
        print(Fore.MAGENTA + f"Error monitoring resources: {str(e)}")

def network_traffic_analyzer():
    try:
        from scapy.all import sniff
        print(Fore.MAGENTA + "\nNetwork Traffic Analyzer")
        print("Capturing packets (Ctrl+C to stop)...")
        packets = sniff(count=10, timeout=30)
        print("\nPacket Summary:")
        for i, packet in enumerate(packets, 1):
            print(f"{i}. {packet.summary()}")
    except Exception as e:
        print(Fore.MAGENTA + f"Error analyzing traffic: {str(e)}")
def matrix_rain():
    import random
    import time
    chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$%^&*()"
    print(Fore.GREEN + "\nMatrix Rain Effect (Ctrl+C to stop)")
    try:
        while True:
            print(''.join(random.choice(chars) for _ in range(70)))
            time.sleep(0.1)
    except KeyboardInterrupt:
        print(Fore.MAGENTA + "\nMatrix rain stopped.")

def retro_console():
    print(Fore.MAGENTA + """
╔════════════════════════════╗
║ RETRO CONSOLE v1.0         ║
║ > Type 'help' for commands ║
╚════════════════════════════╝
    """)
    while True:
        cmd = input(Fore.GREEN + "root@retro:~$ " + Style.RESET_ALL).lower()
        if cmd == "exit":
            break
        elif cmd == "help":
            print(Fore.CYAN + """
Available commands:
- help     : Show this help
- whoami   : Display current user
- date     : Show system date
- clear    : Clear screen
- exit     : Exit console
            """)
        elif cmd == "whoami":
            print(Fore.YELLOW + "root")
        elif cmd == "date":
            print(Fore.YELLOW + time.strftime("%Y-%m-%d %H:%M:%S"))
        elif cmd == "clear":
            print("\n" * 50)

def ascii_art_generator():
    text = input(Fore.MAGENTA + "Enter text to convert to ASCII art: ")
    try:
        ascii_text = pyfiglet.figlet_format(text, font='slant')
        print(Fore.GREEN + ascii_text)
    except Exception as e:
        print(Fore.MAGENTA + f"Error generating ASCII art: {str(e)}")