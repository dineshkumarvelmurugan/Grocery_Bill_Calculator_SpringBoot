package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Item;
import com.example.demo.Repository.ItemRepository;

@Controller
public class GroceryController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/")
    public String index(Model model) {
        // Fetch all items and add them to the model
        model.addAttribute("items", itemRepository.findAll());
        // Add a new empty item for the form
        model.addAttribute("item", new Item());
        return "grocery";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item) {
        // Save the new item to the database
        itemRepository.save(item);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editItemForm(@PathVariable Long id, Model model) {
        // Find the item by ID or throw an exception if not found
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
        // Add the item and all items to the model
        model.addAttribute("item", item);
        model.addAttribute("items", itemRepository.findAll());
        return "grocery";
    }

    @PostMapping("/edit/{id}")
    public String editItem(@PathVariable Long id, @ModelAttribute Item item) {
        // Update the item with the new data and save it
        item.setId(id);
        itemRepository.save(item);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        // Delete the item by ID
        itemRepository.deleteById(id);
        return "redirect:/";
    }
}
