package illvent.backend.wish.service;

import illvent.backend.event.domain.Event;
import illvent.backend.event.domain.EventRepository;
import illvent.backend.member.domain.Member;
import illvent.backend.member.domain.MemberRepository;
import illvent.backend.wish.domain.Wish;
import illvent.backend.wish.domain.WishRepository;
import illvent.backend.wish.dto.WishDeleteRequestDTO;
import illvent.backend.wish.dto.WishRegisterRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;

    private final MemberRepository memberRepository;

    private final EventRepository eventRepository;

    @Transactional
    public Optional<Wish> registerWish(WishRegisterRequestDTO wishRegisterRequestDTO){
        Member member = memberRepository.findById(wishRegisterRequestDTO.getMemberNo()).orElseThrow(() ->
                new IllegalArgumentException("Member not found"));

        Event event = eventRepository.findById(wishRegisterRequestDTO.getEventNo()).orElseThrow(() ->
                new IllegalArgumentException("Event not found"));

        event.updateLikesIncrease();
        eventRepository.save(event);

        Wish wish = Wish.builder()
                .member(member)
                .event(event)
                .build();

        return Optional.of(wishRepository.save(wish));
    }

    @Transactional
    public void deleteWish(WishDeleteRequestDTO wishDeleteRequestDTO){
        Wish wish = wishRepository.findByMemberNoAndEventNo(wishDeleteRequestDTO.getMemberNo(), wishDeleteRequestDTO.getEventNo());

        Event event = wish.getEvent();
        event.updateLikesDecrease();
        eventRepository.save(event);

        wishRepository.delete(wish);
    }
}
