// Data Security and Cryptography (ELEC8660)
// Project 1: Decrypting Vigenere Cipher
// Programmed by Harnish Gemlawala (ID: 110078498)
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
 
public class decryption {
 
	public static void main(String[] args) {
	String cipherText= "YIGBMNITQKGNTGKKITLPGAAJXGXOABTWUEAGVDFMULALBZQSNPKIPBGGOAVGYBUFB"
+ "ZEAFTMQGNGZQCOEAVDRKONIGLZMRZLCMRRFGLECNHTIPBZEOHEJUENGJQSNLZMRVMSQGUMVWSFB"
+ "HTYOKOVGNUUCTNGUBHRKCIRAHZEIGAMMRZTTGTUBYBIZXZPAGBYINRQZZEZXVWSFBHQLVMEINQB"
+ "JWNBMHMLVXBMIABZALVDKTIUHULMLLKTFONZBHNMJWCHFKVTHGJWUOMKLLLBSXLVVGBEFTTCMOX"
+ "XWFBNXATNMKAMRGCPOZPKKAAGUBASYUZDGHNIVRWOACEXJQTRWOVAARCIYNMZPECKKAEAMSWMRG"
+ "ZISNIGZTLVXGFBKRIBBNXQTJHATDOXOZRRLOATVURMAAWGTAOHAZGBOKZNZXTBAGMNQSWNTKTHK"
+ "KEOHEJQNZRUXIABUVBRTMZAIXJQSNUOTIGRLWROKOBIFAZZAQXHCTGAGBIFTSMRRGUBHVGMBOGA"
+ "KZENEJINTXXPECTAAEQTTLTUXTAAVWWCIRMRGYBNSIYCXXPACLNIVRAKIRQHXZENWZPAGMNMRRB"
+ "YJOYLNMVVLZQNSEAMNPXGBWBKQJEUBTLTUXVZEFXTBLNUUCRHGXMSGMAXPRGIMNBWJMDGAGBIFM"
+ "NMTENZPBBEYPEIBYBGBEJQSCHAZIAZOVTBMNQSPHAVTERLWRGAKAPRVONIPIAZPBLKWFCKUKUEB"
+ "TOAEXBWLHMOWNNGJBHRKKQSNVKZTNBTUAATSINJAUAEEXGTNNFKQSHGQVOJGZWUFPNWIFPUZKVG"
+ "MQNGAKLAEDLWRUBYWWAXTLSGAKJOYLNMVVLZAAEXHMHVGJBHREGJOHKAVRRLZJUGMNQSZTTQSOX"
+ "NQNQMNMBBEYPEIBYBSJAUQSUXCMDBGUBKAHCPEVLGTWNRYAPBDKVOSUEBHRNTISFNSQNTMOBLRH"
+ "LUROKUENONZWNRMNQNTBYKEEMGQNUXOATUX";
		
		// Calling the methods to find the number of coincidences, keyLength, 
		//key and decrypt plaintext:
		int keyLength = findKeyLength(cipherText);
		System.out.println("\nKey length is " + keyLength);
 
		String key = findKey(cipherText, keyLength);
		System.out.println("\nKey is: " + key);
 
		String plaintext = decrypt(cipherText, key);
		System.out.println("\nPlaintext is: " + plaintext);
 
	}
 
	// Method to find the keylength & number of coincidences
	public static int findKeyLength(String cipherText) {
		List<Integer> coinc = new ArrayList<Integer>();
		for (int i = 2; i <= 30; i++) {
			int count = 0;
			for (int j = 0; j < cipherText.length() - i; j++) {
				if (cipherText.charAt(j) == cipherText.charAt(j + i)) {
					count += 1;
				}
			}
			coinc.add(count); // a list
		}
 
		
for (int n = 0; n < coinc.size(); n++) {
			System.out.println("The Number of Coincidences for " + (n + 2) + " Shifts are " + coinc.get(n) + "\n");
		}
 
		// Maximum number of coincidence is as follow:
		int maxCoincidence = 0;
		for (int i = 0; i < coinc.size(); i++) {
			if (coinc.get(i) > maxCoincidence) {
				maxCoincidence = coinc.get(i);
			}
		}
 
		// Number of displacements after which coincidences occur
		int shifts = coinc.indexOf(maxCoincidence) + 2; 
		// Maximum number's Index 
 
		// Listing coincidences in Descending order 
		List<Integer> coincidence = new ArrayList<Integer>();
		for (int i = 0; i < coinc.size(); i++) {
			if (!coincidence.contains(coinc.get(i))) {
				coincidence.add(coinc.get(i));
			}
		}
		Collections.sort(coincidence);
		Collections.reverse(coincidence);
		
		int max2 = gcd(coinc.indexOf(maxCoincidence) + 2, coinc.get(coincidence.size() - 2) + 2);
		int max3 = gcd(coinc.indexOf(maxCoincidence) + 2, coinc.get(coincidence.size() - 3) + 2);
		int max4 = gcd(coinc.indexOf(maxCoincidence) + 2, coinc.get(coincidence.size() - 4) + 2);
		List<Integer> list = new ArrayList<Integer>();
		list.add(max2);
		list.add(max3);
		list.add(max4);
 
		return getMaximum(list);
	}
 
	// Function to find the key
	public static String findKey(String cipherText, int keyLength) {
		String key = "";
        double[] englishFrequency = {8.2, 1.5, 2.8, 4.3, 13, 2.2, 2, 6.1, 7, 0.15, 0.77, 4, 2.4, 6.7, 7.5, 1.9, 0.095, 6, 6.3, 9.1, 2.8, 0.98, 2.4, 0.15, 2, 0.074};
        for (int k = 0; k < keyLength; k++) {
            String[] subStr = new String[keyLength];
            for (int i = 0; i < keyLength; i++) {
                subStr[i] = "";
                for (int j = i; j < cipherText.length(); j += keyLength) {
                    subStr[i] += cipherText.charAt(j);
                }
            } 

            int empStr[] = new int[26];

        	Arrays.fill(empStr,0);
            
            //A loop to convert ASCII characters to integers 
            //Also count the frequency of each letter to error with 
		 //compute english language frequencies 
            for (char c:subStr[k].toCharArray()) {
            	int letter = (int) c;
            	empStr[letter-65] += 1; 
            }
            
            //Here errors are calculated using english language frequency analysis
            double[] error = new double[26];
            for (int shifts = 0; shifts < 26; shifts++) {
                double err = 0;
                for (int i = 0; i < 26; i++) {
                    err += Math.sqrt(Math.pow(empStr[i] - englishFrequency[(i + shifts) % 26], 2));
                }
                error[shifts] = err;
            }
            
            //Lowest error frequencies are mapped with closest alphabets 
		 //(Guessing the best fits)
            int guess = 0;
            double bestFit = error[0];
            for (int i = 0; i < 26; i++) {
                if (error[i] < bestFit) {
                    guess = i;
                    bestFit = error[i];
                }
            }
            key += (char) ((26 - guess) % 26 + 65);
        }
        return key;
	}
 
	//Decryption code for deciphering cipher text using recovered key
	public static String decrypt(String cipherText, String key) {  
		String plaintext = "";
		for (int i = 0; i < cipherText.length(); i++) {
			int shift = key.charAt(i % key.length()) - 'A';
			char c = cipherText.charAt(i);
			char p = (char) ('A' + (c - 'A' - shift + 26) % 26);
			plaintext += p;
		}
		return plaintext;
	}

	//Finding GCD for 3 inputs
	public static int gcd(int a, int b, int c) { 
		return gcd(gcd(a, b), c);
	}
	//Finding GCD for 2 inputs
	public static int gcd(int a, int b) { 
		
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}

	// A General Function to find maximum integer in a List
	public static int getMaximum(List<Integer> list) {
		int max = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) > max) {
				max = list.get(i);
			}
		}
		return max;
	}
 
}
