INSERT INTO `user` (`id`, `username`, `password`, `create_date`)
VALUES (1, 'admin', '$2a$12$4Fdgi6G6RaYKlYsjhXF9yOWepb/nSykscYiYQdbrEnl3J07qA/VGa', now()),
       (2, 'test', '$2a$12$4Fdgi6G6RaYKlYsjhXF9yOWepb/nSykscYiYQdbrEnl3J07qA/VGa', now());


INSERT INTO `user_role` (`id`, `user_id`, `role`, `create_date`)
VALUES (1, 1, 'ADMIN', now()),
       (2, 1, 'USER', now()),
       (3, 2, 'USER', now());


INSERT INTO `todo` (`id`, `user_id`, `content`, `done_yn`, `create_date`)
VALUES (1, 2, '일어나기', 'Y', now()),
       (2, 2, '양치하기', 'Y', now()),
       (3, 2, '샤워하기', 'N', now()),
       (4, 2, '출근하기', 'N', now()),
       (5, 2, '퇴근하기', 'N', now());