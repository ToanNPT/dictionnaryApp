ALTER TABLE public.word_descr ALTER COLUMN description TYPE text USING description::text;