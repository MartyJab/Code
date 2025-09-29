from Crypto.Cipher import AES

plaintext = bytes.fromhex('38a6871be92576a0e96a35a34a216d74')  # 16 bytes

#Key maximum is 65535
secret_key_int = 65535
secret_key = secret_key_int.to_bytes(16, 'big')
cipher = AES.new(secret_key, AES.MODE_ECB)
ciphertext = cipher.encrypt(plaintext)
print("target ciphertext (hex):", ciphertext.hex())

found = None
for i in range(0x10000):
    key = i.to_bytes(16, 'big')
    c = AES.new(key, AES.MODE_ECB).encrypt(plaintext)
    if c == ciphertext:
        found = key
        print("Key found! i =", i, " key (hex):", key.hex())
        break

if found is None:
    print("No key found.")
