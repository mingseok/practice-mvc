package com.example.practicemvc.web.validation;

import com.example.practicemvc.domain.item.Item;
import com.example.practicemvc.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;

    /**
     * WebDataBinder에 검증기를 추가하면 해당 컨트롤러에서는 검증기를 자동으로 적용할 수 있다.
     * 이 컨트롤러가 호출이 될때 마다 항상 @InitBinder가 불려진다.
     * 그리하여 계속 dataBinder.addValidators(itemValidator); 인 검증기가 실행 하는 것이다.
     * 다시 말해, 여기 컨트롤러에서만 모든 메서드에 검증기를 도입 할 수 있는 것이다.
     */
    @InitBinder
    public void init(WebDataBinder dataBinder) {
        // dataBinder.addValidators(itemValidator);
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    //


    /**
     * @Validated란? @ModelAttribute Item 에 대해서 자동으로 검증기가 수행이 되는 것이다.
     * 그리하여 검증 다하고 문제가 있다면, BindingResult bindingResult 에 담기는 것이다.
     * 이렇게 할 수 있었던 이유는, @InitBinder 메서드 내부로직에 검증기가 있기 때문에 가능한 것이다.
     * @Validated는 검증 대상 앞에 붙어야 되는 것이다.
     * @Validated는 "검증기를 실행하라" 라는 애노테이션이다.
     * @Validated 와 @Valid 는 동일하다. 둘다 사용해도 된다.
     */
//    @PostMapping("/add")
//    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult
//            bindingResult, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            log.info("errors={}", bindingResult);
//            return "validation/v2/addForm";
//        }
//        //성공 로직
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

