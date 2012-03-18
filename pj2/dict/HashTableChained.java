    /* HashTableChained.java */
    package dict;

    import list.*;

    /**
     *  HashTableChained implements a Dictionary as a hash table with chaining.
     *  All objects used as keys must have a valid hashCode() method, which is
     *  used to determine which bucket of the hash table an entry is stored in.
     *  Each object's hashCode() is presumed to return an int between
     *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
     *  implements only the compression function, which maps the hash code to
     *  a bucket in the table's range.
     *
     *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
     **/

    public class HashTableChained implements Dictionary {

        /**
         *  Place any data fields here.
         **/
        private int buckets;
        private List[] table;

        /** 
         *  Construct a new empty hash table intended to hold roughly sizeEstimate
         *  entries.  (The precise number of buckets is up to you, but we recommend
         *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
         **/

        public HashTableChained(int sizeEstimate) {
        // Your solution here.
            double loadFactor = 2.0;
            int buckets = sizeEstimate;
            while(loadFactor > 0.75){
                buckets = nextPrime(buckets);
                loadFactor = (double) sizeEstimate/buckets;
            }
            this.buckets = buckets;
            this.table = new SList[buckets];
        }

        /** 
         *  Construct a new empty hash table with a default size.  Say, a prime in
         *  the neighborhood of 100.
         **/

        public HashTableChained() {
        // Your solution here.
            this.buckets = 101;
            this.table = new SList[buckets];
        }
        
        private boolean isPrime(int i){
            int n = 2;
            while(n*n < i){
               if(i/n == ((float)i)/n){
                   return false;
                }
                n += 1;
            }
            return true;
        }

        private int nextPrime(int i){
        //get the next prime number after i
            i += 1;
            while(!isPrime(i)){
                i += 1;
            }
            return i;
        }

        /**
         *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
         *  to a value in the range 0...(size of hash table) - 1.
         *
         *  This function should have package protection (so we can test it), and
         *  Should be used by insert, find, and remove.
         **/

        int compFunction(int code) {
        // Replace the following line with your solution.
        //ensure p << buckets, I get p by getting the next prime after n*100;
            int comp = Math.abs(((3*code + 7) % nextPrime(this.buckets*100)) % this.buckets);
            return comp;
        }

        /** 
         *  Returns the number of entries stored in the dictionary.  Entries with
         *  the same key (or even the same key and value) each still count as
         *  a separate entry.
         *  @return number of entries in the dictionary.
         **/

        public int size() {
        // Replace the following line with your solution.
            int index = 0;
            int total = 0;
            while(index < this.buckets){
                if(this.table[index] != null){
                    total += ((List) this.table[index]).length();
                }
                index += 1;
            }
            return total;
        }

        /** 
         *  Tests if the dictionary is empty.
         *
         *  @return true if the dictionary has no entries; false otherwise.
         **/

        public boolean isEmpty() {
        // Replace the following line with your solution.
            if(this.size() == 0){
                return true;
            }else{
                return false;
            }
        }

        /**
         *  Create a new Entry object referencing the input key and associated value,
         *  and insert the entry into the dictionary.  Return a reference to the new
         *  entry.  Multiple entries with the same key (or even the same key and
         *  value) can coexist in the dictionary.
         *
         *  This method should run in O(1) time if the number of collisions is small.
         *
         *  @param key the key by which the entry can be retrieved.
         *  @param value an arbitrary object.
         *  @return an entry containing the key and value.
         **/

        public Entry insert(Object key, Object value) {
        // Replace the following line with your solution.
            Entry cur = new Entry();
            cur.key = key;
            cur.value = value;
            int index = compFunction(key.hashCode());
            if(this.table[index] == null){
                this.table[index] = new SList();
            }
            this.table[index].insertFront(cur);
            return cur;
        }

        /** 
         *  Search for an entry with the specified key.  If such an entry is found,
         *  return it; otherwise return null.  If several entries have the specified
         *  key, choose one arbitrarily and return it.
         *
         *  This method should run in O(1) time if the number of collisions is small.
         *
         *  @param key the search key.
         *  @return an entry containing the key and an associated value, or null if
         *          no entry contains the specified key.
         **/

        public Entry find(Object key) {
        // Replace the following line with your solution.
            int index = compFunction(key.hashCode());
            try{
                ListNode bucket = this.table[index].front(); //throws NullPointerException if table[index] == null
                while(true){
                if(((Entry) bucket.item()).key().equals(key)){
                    return (Entry) bucket.item();
                }
                    bucket = bucket.next();
                }
            } catch (InvalidNodeException e) {
                return null;
            } catch (NullPointerException e) {
                return null;
            }
        }

        /** 
         *  Remove an entry with the specified key.  If such an entry is found,
         *  remove it from the table and return it; otherwise return null.
         *  If several entries have the specified key, choose one arbitrarily, then
         *  remove and return it.
         *
         *  This method should run in O(1) time if the number of collisions is small.
         *
         *  @param key the search key.
         *  @return an entry containing the key and an associated value, or null if
         *          no entry contains the specified key.
         */

        public Entry remove(Object key) {
        // Replace the following line with your solution.
            int index = compFunction(key.hashCode());
            try{
                ListNode bucket = this.table[index].front(); //throws NullPointerException if table[index] == null
                while(true){
                    if(((Entry) bucket.item()).key().equals(key)){
                        Entry toRemove = (Entry) bucket.item();
                        bucket.remove(); //remove the first entry found
                        return toRemove;
                    }
                    bucket = bucket.next();
                }
            } catch (InvalidNodeException e) {
                return null;
            } catch (NullPointerException e) {
                return null;
            }
        }

        /**
         *  Remove all entries from the dictionary.
         */
        public void makeEmpty() {
        // Your solution here.
        //I am not sure if I should do this or remove all the elements individually but I favor the former
            this.table = new SList[this.buckets];
        }

    }
