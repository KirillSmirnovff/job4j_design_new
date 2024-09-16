package ru.job4j.generics;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RoleStoreTest {

    @Test
    public void whenAddAndFindThenRoleIsSystemAdmin() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "System Admin"));
        Role result = store.findById("1");
        assertThat(result.getRole()).isEqualTo("System Admin");
    }

    @Test
    public void whenAddAndFindThenRoleIsNull() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "System Admin"));
        Role result = store.findById("10");
        assertThat(result).isNull();
    }

    @Test
    public void whenAddDuplicateAndFindRoleIsSystemAdmin() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "System Admin"));
        store.add(new Role("1", "Advanced User"));
        Role result = store.findById("1");
        assertThat(result.getRole()).isEqualTo("System Admin");
    }

    @Test
    public void whenReplaceThenRoleIsAdvancedUser() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "System Admin"));
        store.replace("1", new Role("1", "Advanced User"));
        Role result = store.findById("1");
        assertThat(result.getRole()).isEqualTo("Advanced User");
    }

    @Test
    public void whenNoReplaceRoleThenNoChangeRole() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "System Admin"));
        store.replace("10", new Role("10", "Advanced User"));
        Role result = store.findById("1");
        assertThat(result.getRole()).isEqualTo("System Admin");
    }

    @Test
    public void whenDeleteRoleThenRoleIsNull() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "System Admin"));
        store.delete("1");
        Role result = store.findById("1");
        assertThat(result).isNull();
    }

    @Test
    public void whenNoDeleteRoleThenRoleIsSystemAdmin() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "System Admin"));
        store.delete("10");
        Role result = store.findById("1");
        assertThat(result.getRole()).isEqualTo("System Admin");
    }
}