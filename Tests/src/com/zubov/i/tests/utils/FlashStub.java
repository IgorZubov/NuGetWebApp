package com.zubov.i.tests.utils;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FlashStub extends Flash {

    private Map<String, Object> _map = new HashMap<>();

    public FlashStub(){

    }

    @Override
    public Object get(Object key) {
        if (!_map.containsKey(key))
            return null;
        Object val =  _map.get(key);
        _map.remove(key);
        return val;
    }

    @Override
    public Object put(String key, Object value) {
        _map.put(key, value);
        return value;
    }

    @Override
    public boolean isKeepMessages() {
        return false;
    }

    @Override
    public void setKeepMessages(boolean b) {

    }

    @Override
    public boolean isRedirect() {
        return false;
    }

    @Override
    public void setRedirect(boolean b) {

    }

    @Override
    public void putNow(String s, Object o) {

    }

    @Override
    public void keep(String s) {

    }

    @Override
    public void doPrePhaseActions(FacesContext facesContext) {

    }

    @Override
    public void doPostPhaseActions(FacesContext facesContext) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Object> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return null;
    }
}
