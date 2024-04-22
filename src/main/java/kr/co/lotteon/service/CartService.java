package kr.co.lotteon.service;

import kr.co.lotteon.dto.CartDTO;
import kr.co.lotteon.entity.Cart;
import kr.co.lotteon.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    
    // 장바구니 삽입
    public void insertCart(CartDTO cartDTO) {
        List<Cart> existingCartItems = cartRepository.findCartByUidAndPno(cartDTO.getUid(), cartDTO.getPno());

        if(existingCartItems.isEmpty()){
            // 해당 상품이 장바구니에 없으면 새로 추가
            Cart cartItem = new Cart();
            cartItem.setUid(cartDTO.getUid());
            cartItem.setPno(cartDTO.getPno());
            cartItem.setPcount(cartDTO.getPcount());
            cartItem.setOptions(cartDTO.getOptions());
            log.info("cartItem : " + cartItem);
            cartRepository.save(cartItem);
        } else {
            // 해당 상품이 장바구니에 있으면 수량 추가 또는 새로운 상품으로 추가
            for (Cart existingCartItem : existingCartItems) {
                if (existingCartItem.getPno() == cartDTO.getPno()) { // 상품 번호가 일치하는 경우
                    int newPcount = existingCartItem.getPcount() + cartDTO.getPcount();
                    existingCartItem.setPcount(newPcount);
                    cartRepository.save(existingCartItem);

                    log.info("existingCartItem : " + existingCartItem);
                    break;
                }
            }
        }
    }

}











