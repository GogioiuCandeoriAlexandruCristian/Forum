INSERT INTO `forum`.`roles` (`id`, `name`) VALUES ('1', 'ROLE_USER');
INSERT INTO `forum`.`roles` (`id`, `name`) VALUES ('2', 'ROLE_ADMIN');

INSERT INTO `forum`.`categories` (`id`, `title`) VALUES ('1', 'Category One');
INSERT INTO `forum`.`categories` (`id`, `title`) VALUES ('2', 'Category Two');
INSERT INTO `forum`.`categories` (`id`, `title`) VALUES ('3', 'Category Three');

INSERT INTO `forum`.`technologies` (`id`, `title`, `categ_id`) VALUES ('1', 'Tech 1', '1');
INSERT INTO `forum`.`technologies` (`id`, `title`, `categ_id`) VALUES ('2', 'Tech 2', '1');
INSERT INTO `forum`.`technologies` (`id`, `title`, `categ_id`) VALUES ('3', 'Tech 3', '2');
INSERT INTO `forum`.`technologies` (`id`, `title`, `categ_id`) VALUES ('4', 'Tech 4', '3');
INSERT INTO `forum`.`technologies` (`id`, `title`, `categ_id`) VALUES ('5', 'Tech 5', '3');
INSERT INTO `forum`.`technologies` (`id`, `title`, `categ_id`) VALUES ('6', 'Tech 6', '3');


INSERT INTO `forum`.`topics` (`id`, `question`, `title`, `tech_id`, `user_id`) VALUES ('1', 'question 1', 'Title 1', '1', '1');
INSERT INTO `forum`.`topics` (`id`, `question`, `title`, `tech_id`, `user_id`) VALUES ('2', 'question 2', 'Ttile 2', '1', '1');

INSERT INTO `forum`.`answers` (`id`, `points`, `rating`, `text`, `topic_id`, `user_id`) VALUES ('1',`0`, `0`, 'dsasdas', '1', '1');
