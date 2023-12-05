

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Node<K extends Comparable<K>, V> {
    // K key ==> V value

    private K key;
    private V value;

    private Node<K, V> right;
    private Node<K, V> left;

    public Node(K key_arg, V value_arg) {
        key = key_arg;
        value = value_arg;
        left = null;
        right = null;
    }

    private Node<K, V> findTargetNode(K key_arg) {
        //will return the node that contains the current key "key" or the node
        // that would be the parent of a new node with key
        // return value indicates which, and left/right
        Node<K, V> tmp = this;

        int direction = key_arg.compareTo(tmp.key);
        boolean targetFound = (direction == 0);

        while (!targetFound) {
            direction = key_arg.compareTo(tmp.key);
            if (direction == 0) { //find the node either mapping we replace or parent we gonna add
                targetFound = true;
            } else if (direction < 0) {
                if (tmp.left == null)
                    targetFound = true;
                else
                    tmp = tmp.left;
            } else {
                if (tmp.right == null)
                    targetFound = true;
                else
                    tmp = tmp.right;
            }
        }
        return tmp;
    }

    public V put(K key_arg, V value_arg, AtomicBoolean added) {
        Node<K, V> targetnode = findTargetNode(key_arg);

        int direction = key_arg.compareTo(targetnode.key);

        if (direction == 0) {
            //replace key to value mapping
            V original = targetnode.value;
            targetnode.value = value_arg;
            added.set(false);
            return original;
        } else if (direction < 0) {
            //added new left child
            added.set(true);
            targetnode.left = new Node<>(key_arg, value_arg);
            return null;
        } else {
            //add new right child
            added.set(true);
            targetnode.right = new Node<>(key_arg, value_arg);
            return null;
        }
    }

    public boolean nodeContainsKey(K key) {
        Node<K, V> node = findKeyNode(key, null);
        return (node!= null);
    }

    private Node<K, V> findKeyNode(K key, AtomicReference<Node<K, V>> parentWrapper) {
        Node<K, V> tmp = this;

        while ((tmp != null) && (key.compareTo(tmp.key) != 0)) {
            if (parentWrapper != null)
                parentWrapper.set(tmp);
            if (key.compareTo(tmp.key) < 0)
                tmp = tmp.left;
            else
                tmp = tmp.right;
        }

            return tmp;
    }

    public V nodeGet(K key) {
        Node<K, V> node = findKeyNode(key, null);

        if (node == null)
            return null;
        else
            return node.value;

    }

    public boolean nodeCanFindValue(V value_arg) {
        // boolean found = false;
        // return ((value_arg == null && value == null) ||
        //         (value_arg != null && value != null && value_arg.equals(value)) ||
        //         (left != null && left.nodeCanFindValue(value_arg)) ||
        //          (right != null && right.nodeCanFindValue(value_arg)));

        if (value_arg == null && value == null)
            return true;

        if (value_arg != null && value_arg.equals(value))
            return true;

        if (left != null) // current node isn't it, check all items in left
            if(left.nodeCanFindValue(value_arg))
                return true;

        if (right != null)
            if (right.nodeCanFindValue(value_arg))
                return true;
        // Otherwise... unable to find match here or in the left subtree
        return false;

    }

    public void addAllValues(Collection<V> bag) {
        // this node has a value to keep...
        bag.add(value);
        if (left != null)
            left.addAllValues(bag);
        if (right != null)
            right.addAllValues(bag);
    }

    public void addAllKeys(Set<K> set) {
        // this node has a key to keep...
        set.add(key);
        if (left != null)
            left.addAllKeys(set);
        if (right != null)
            right.addAllKeys(set);
    }

    private void clipOutEasyNode(Node<K, V> node, Node<K, V> parent) {
        // First compute pointer to the remaining child i have
        Node<K, V> remainingChild = node.left;
        if (node.left == null)
            remainingChild = node.right;

        if (parent == null) { //copy possible child values to here and remove child
            if (node.right != null) {
                node.key = remainingChild.key;
                node.value = remainingChild.value;
                node.left = remainingChild.left;
                node.right = remainingChild.right;
            }
        } else { // we do have a parent

            if (parent.left == node) // We are left child
                parent.left = remainingChild; // Might be null when no children at all
            else
                parent.right = remainingChild;
        }
    }


    private Node<K, V> findPredecessor(AtomicReference<Node<K, V>> parentWrapper) {
        Node <K, V> predNode = this;

        if (left != null) {
            parentWrapper.set(this);
            predNode = left;
            while (predNode.right != null) {
                parentWrapper.set(predNode);
                predNode = predNode.right;
            }
        }
        return predNode;
    }
    public V removeMap(K key, AtomicBoolean somethingRemoved) {

        // Find mapping to be removed ( and its parents)
        AtomicReference<Node<K, V>> parentWrapper = new AtomicReference<>(null);
        Node <K, V> keyNode = findKeyNode(key, parentWrapper);

        // If nothing found return
            if (keyNode == null) {
                somethingRemoved.set(false);
                return null;
            }

            // capture return values of found mapping
            V retValue = keyNode.value;
            somethingRemoved.set(true);

            // find suitable replacement node

            Node<K, V> predNode = keyNode.findPredecessor(parentWrapper);

            // swap replacement node contents into existing map node
            if (keyNode != predNode) {
                keyNode.key = predNode.key;
                keyNode.value = predNode.value;
            }
            // no matter what remove replacement node
                clipOutEasyNode(predNode, parentWrapper.get());
            // return captured value prior to being overwritten
            return retValue;
        }
    }


