inductive Data where
  /- Declare every piece of information here with a brief and simple name -/
  | movies              /- content of movies.txt (general movie list) -/
  | movies_perso        /- content of movies.perso.txt (the user's personal picks/opinions) -/
  | liked_movies        /- the movies the user likes, with their notes -/
  | blog_outline        /- structure of the blog post: intro, one section per movie, conclusion -/
  | blog_post           /- the final blog post in Markdown -/

inductive Action where
  | list_dir (path: String) /- return the output of ls <path> -/
  | read (path : String) (save_as: Data) /- read the file at <path> -/
  | write (data: Data) (path: String) /- <data> is written in <path> -/
  | display (data: Data) /- <data> is displayed to the user -/
  | think (sources: List Data) (save_as: Data) /- think with <sources> and save results in <save_as> -/
  | get_request (url: String) /- request <url> with HTTP GET method -/
  | new_plan /- stop the current plan and generate a new plan with the intels collected before -/

abbrev Plan := List Action

open Action Data in
def plan : Plan := [
  read "movies.perso.txt" movies_perso,
  read "movies.txt" movies,
  think [movies_perso, movies] liked_movies,
  think [liked_movies] blog_outline,
  think [blog_outline, liked_movies] blog_post,
  write blog_post "blog_post.md",
  display blog_post
]
